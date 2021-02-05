# 在单台机器上实现TB级的数据对比之数据切分

### 文件切分的逻辑

由于面对的原始文件可能是一堆小文件，也可能是一个很大文件，这第一步当然是对文件进行切割规整，统一大小为32M，这个32M是一个近似值，由于要保留完整的行，故这是一个接近值。那如何实现呢？

分为两部分来说吧，

第一部分是文件读取：

1. 读取目录下的文件，获得一个文件列表。列表使用数组来存储。
2. 将列表按文件名进行排序，获得数据读取文件的一个顺序，也就是数据的一个顺序。
3. 声明一个索引编号，编号从0开始，这个索引编号代表的就是当前正在读取的文件。
4. 按索引编号在文件列表中读取。打开字符流。
5. 按行读取文件。直到一个文件结束。
6. 当一个文件读取结束时，关闭当前文件流。将文件索引编号加1，再次打开下一个文件的字符流。
7. 继续按行读取。直到所有文件都结束。
8. 当文件操作完毕后，数据流将自动关闭。

第二部分是文件写入：与文件读取是类似的打开

1. 当然是指定文件的写入路径了，以及文件名增长规则。我使的规则较简单为:xxx-0.txt.ing和xxx-0.txt，在写入中以.ing结束，写入完成的被重命名为xxx.txt。
2. 声明一个写入索引。编号从0开始。这个索引代表的就是当前正常写入的文件，这个索引编号是文件名的一部分。
3. 打开写入字符流。
4. 按行进行数据的写入。直到文件到达临界值32M。
5. 当达到32M时，关闭当前文件流，文件重命名，将.ing去除。再将写入索引加1，再次打下写入文件字符流。
6. 继续按行写入，直接所有文件都写入完成。
7. 当文件操作完毕后，数据流将自动关闭。

最后我解释下为什么是32M。其实这个我是参照了HDFS的文件块的大小设计。在内存占用与磁盘传输方面取了一个折中值。
当然如果内存允许，可以将这个值调的更大，64M，128M都是可以的。
这个受到两方面的一个限制
限制1：单文件如果过大，那么加载到内存中排序的内存需求量也要更大。在普通机器这个限制较多。毕竟不是服务器，内存都128G起步的。
限制2：单文件如果过小，那以文件的IO效率必然降低，我们都知道，磁盘这种设备顺序读写速度较高，随机访问却很低。基于这个特性单文件就不能太小。否则IO会成为瓶颈。


### 示例图

联合起来就是就是下面这样子：

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序-文件拆分写入.png)

这就是文件切分的一个过程。



### 读取的代码实现

最后来看看代码吧！

```java
/**
 * 多文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileReader implements AutoCloseable {

  /** 每次读取0124条记录 */
  private static final int BATCH_READ_LIST = 1024;

  /** 基础的文件路径 */
  private String path;

  /** 文件操作路径 */
  private final File[] fileData;

  /** 当前读取的索引编号 */
  private int readIndex;

  /** 字符流的读取 */
  private FileReader fileReader;

  /** 带缓冲区的字符流 */
  private BufferedReader bufferedReader;

  public ManyFileReader(String path) {
    this.path = path;
    File[] fileDataTmp = FileUtils.getFileList(this.path);
    // 执行默认的文件排序操作
    Arrays.sort(
        fileDataTmp,
        (o1, o2) -> {
          int index1 = getFileIndex(o1);
          int index2 = getFileIndex(o2);
          if (index1 < index2) {
            return -1;
          } else if (index1 > index2) {
            return 1;
          }
          return 0;
        });
    this.fileData = fileDataTmp;
    this.readIndex = 0;
  }

  /**
   * 读取，带排序的操作
   *
   * @param path
   * @param comparator
   */
  public ManyFileReader(String path, Comparator<File> comparator) {
    this.path = path;
    this.readIndex = 0;
    File[] files = FileUtils.getFileList(this.path);
    // 执行文件的排序操作
    Arrays.sort(files, comparator);
    this.fileData = files;
  }

  /** 文件通道打开，首次操作，需要明确调用打开文件通道 */
  public void openFile() {
    if (this.readIndex >= this.fileData.length) {
      return;
    }

    try {
      this.fileReader = new FileReader(this.fileData[readIndex]);
      this.bufferedReader = new BufferedReader(this.fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 每次读取一行数据
   *
   * @return
   */
  public String readLine() throws IOException {
    String dataLine = null;

    dataLine = this.bufferedReader.readLine();

    // 文件读取行结束，切换到下一行
    if (dataLine == null) {
      boolean nextFile = this.nextFile();
      // 当文件切换成功后,需要再读取一次数据，保证连续读取
      if (nextFile) {
        dataLine = this.bufferedReader.readLine();
      }
    }

    return dataLine;
  }

  /**
   * 批量读取数据
   *
   * @return 批量数据集
   * @throws IOException 异常信息
   */
  public List<String> readLineList() throws IOException {
    List<String> dataList = new ArrayList<>(BATCH_READ_LIST);

    for (int i = 0; i < BATCH_READ_LIST; i++) {
      String dataLine = this.readLine();
      if (null != dataLine) {
        dataList.add(dataLine);
      } else {
        break;
      }
    }
    return dataList;
  }

  /** 文件再次重新读取 */
  public void reload() {
    this.readIndex = 0;
  }

  /** 文件关闭操作 */
  @Override
  public void close() {
    if (null != this.bufferedReader) {
      IOUtils.close(this.bufferedReader);
    }
    if (null != this.fileReader) {
      IOUtils.close(this.fileReader);
    }
  }

  /**
   * 切换到下一个文件
   *
   * @return
   */
  private boolean nextFile() {
    // 切换到一个文件索引
    this.readIndex++;
    // 当文件读取索引大于文件个数时，执行退出
    if (this.readIndex >= this.fileData.length) {
      return false;
    }
    // 1,当一个文件读取完成后，执行对文件的关闭操作
    this.close();
    // 切换成功后，打开下一个文件通道
    this.openFile();

    return true;
  }

  /**
   * 默认按文件索引号进行排序
   *
   * @param file
   * @return
   */
  private int getFileIndex(File file) {
    String name = file.getName();
    int suffixIndex = name.lastIndexOf(Symbol.POINT);
    int spitIndex = name.lastIndexOf(Symbol.MINUS);
    return Integer.parseInt(name.substring(spitIndex + Symbol.MINUS.length(), suffixIndex));
  }
}
```



### 写入的代码实现

文件写入,由于文件写入我支持两种模式，一种为按行切分，还有一种为按大小切分。故将写入的基础类义为一个抽象类

```java
/**
 * 多文件写入操作,基础类
 *
 * @author liujun
 * @version 0.0.1
 */
public abstract class AbstractManyFileWrite implements AutoCloseable {

  /** 正在文件写入标识 */
  private static final String WRITE_ING_FLAG = CompareConfig.TEXT_SUFFIX_NAME_ING;

  /** 写入的文件后缀名 */
  private static final String WRITE_FILE_SUFFIX_NAME = CompareConfig.TEXT_SUFFIX_NAME;

  /** 基础的文件路径 */
  private final String path;

  /** 输出的文件名 */
  private final String fileName;

  /** 当前写入的编写 */
  private AtomicInteger writeIndex;

  /** 文件写入 */
  private FileWriter writer;

  /** 带缓冲区的文件写入 */
  private BufferedWriter bufferedWriter;

  /**
   * 多路文件写入
   *
   * @param path 文件路径
   * @param fileName 文件名称
   */
  public AbstractManyFileWrite(String path, String fileName) {
    this.path = path;
    this.fileName = fileName;
    this.writeIndex = new AtomicInteger(0);
  }

  /** 文件打开操作 */
  public void openFile() {
    try {
      this.writer = new FileWriter(this.getFileNameWritePath());
      this.bufferedWriter = new BufferedWriter(this.writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 数据写入操作，不需添加换行符，所有在写入时统一添加换行符
   *
   * <p>目前采用最土办法，直接串行输出,以避免复杂的处理逻辑
   *
   * @param data 数据内容
   */
  public void writeLine(String data) throws IOException {
    String outData = data + Symbol.LINE;
    // 检查是否需要进行切换新文件
    if (this.checkSwitchFile(outData)) {
      // 切换到下一个文件
      nextFile();
    }

    // 数据写入操作
    bufferedWriter.write(outData);

    // 更新容量信息
    this.newSet(outData);
  }

  /**
   * 检查是否切换下一个文件
   *
   * @param data
   * @return
   */
  protected abstract boolean checkSwitchFile(String data);

  /**
   * 最新的数据设置
   *
   * @param data
   */
  protected abstract void newSet(String data);

  /** 进行当前标识的重置操作 */
  protected abstract void reset();

  /** 切换到下一个文件 */
  private void nextFile() {
    IOUtils.close(this.bufferedWriter);
    IOUtils.close(this.writer);

    String oldFilePath = getFileNameWritePath();
    String oldReName = getFileName();

    // 执行重命名旧文件
    FileUtils.rename(oldFilePath, oldReName);
    // 切换到下一文件
    this.writeIndex.incrementAndGet();
    // 在多线程并行时，使用比较交换解决并发问题

    // 标识重置操作
    this.reset();

    // 再次打开文件
    this.openFile();
  }

  /**
   * 获取当前正在写入的文件路径
   *
   * @return 文件名
   */
  private String getFileNameWritePath() {
    StringBuilder fileNameOut = new StringBuilder();
    fileNameOut.append(this.path);
    fileNameOut.append(Symbol.PATH);
    fileNameOut.append(getFileName());
    fileNameOut.append(WRITE_ING_FLAG);
    return fileNameOut.toString();
  }

  /**
   * 获取最终的文件名
   *
   * @return 文件名
   */
  private String getFileName() {
    StringBuilder fileNameOut = new StringBuilder();
    fileNameOut.append(this.fileName);
    fileNameOut.append(Symbol.MINUS);
    fileNameOut.append(writeIndex.get());
    fileNameOut.append(WRITE_FILE_SUFFIX_NAME);
    return fileNameOut.toString();
  }

  /** 文件关闭操作 */
  @Override
  public void close() {
    if (null != this.bufferedWriter) {
      IOUtils.close(this.bufferedWriter);
    }
    if (null != this.writer) {
      IOUtils.close(this.writer);
    }

    // 最终关闭时，进行文件重命名操作
    FileUtils.rename(getFileNameWritePath(), getFileName());
  }
}
```



按大小切分的类

```java
/**
 * 按大小进行文件的切割
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileWriteSize extends AbstractManyFileWrite {

  /** 默认的文件大小32M */
  public static long DEFAULT_FILE_SIZE = 32 * 1024 * 1024;

  /** 切分的文件大小 */
  private final long spitFileSize;

  /** 当前文件大小 */
  private AtomicLong fileSize;

  /**
   * 使用默认的大小切换大小
   *
   * @param path
   * @param fileName
   */
  public ManyFileWriteSize(String path, String fileName) {
    this(path, fileName, DEFAULT_FILE_SIZE);
  }

  public ManyFileWriteSize(String path, String fileName, long spitFileSize) {
    super(path, fileName);
    this.spitFileSize = spitFileSize;
    this.fileSize = new AtomicLong(0);
  }

  @Override
  protected boolean checkSwitchFile(String data) {
    int dataLength = data.getBytes().length;
    return this.fileSize.get() + dataLength > spitFileSize;
  }

  @Override
  protected void newSet(String data) {
    int dataLength = data.getBytes().length;
    // 执行原子级的加法
    fileSize.addAndGet(dataLength);
  }

  @Override
  protected void reset() {
    this.fileSize.set(0);
  }
}
```



按行切分

```java
/**
 * 按大小进行文件的切割
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileWriteSize extends AbstractManyFileWrite {

  /** 默认的文件大小32M */
  public static long DEFAULT_FILE_SIZE = 32 * 1024 * 1024;

  /** 切分的文件大小 */
  private final long spitFileSize;

  /** 当前文件大小 */
  private AtomicLong fileSize;

  /**
   * 使用默认的大小切换大小
   *
   * @param path
   * @param fileName
   */
  public ManyFileWriteSize(String path, String fileName) {
    this(path, fileName, DEFAULT_FILE_SIZE);
  }

  public ManyFileWriteSize(String path, String fileName, long spitFileSize) {
    super(path, fileName);
    this.spitFileSize = spitFileSize;
    this.fileSize = new AtomicLong(0);
  }

  @Override
  protected boolean checkSwitchFile(String data) {
    int dataLength = data.getBytes().length;
    return this.fileSize.get() + dataLength > spitFileSize;
  }

  @Override
  protected void newSet(String data) {
    int dataLength = data.getBytes().length;
    // 执行原子级的加法
    fileSize.addAndGet(dataLength);
  }

  @Override
  protected void reset() {
    this.fileSize.set(0);
  }
}
```



最后就是写义了两个工厂方法，用来获取按大小和按行切分的文件实例

```java
/**
 * 文件写操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileWrite {

  /**
   * 按文件大小进行切分操作,使用默认大小32M
   *
   * @param path 路径
   * @param fileNme 文件名
   * @return 切分对象
   */
  public static AbstractManyFileWrite manyFileWriteBySize(String path, String fileNme) {
    return new ManyFileWriteSize(path, fileNme);
  }

  /**
   * 按文件按行进行切分操作,使用默认2万行
   *
   * @param path 路径
   * @param fileNme 文件名
   * @return 切分对象
   */
  public static AbstractManyFileWrite manyFileWriteByLine(String path, String fileNme) {
    return new ManyFileWriteLine(path, fileNme);
  }
}
```



### 读写调用

最后再来看下文件切分的调用吧:

```java

  /** 去重的前的数据 */
  private static final String UNIQUE_DATA_OUT_PATH = "unique-data-before";

  /**
   * 执行文件的切分操作
   *
   * @param path 原始路径
   * @param spitOutPath 分隔后的路径
   */
  private void spitFile(String path, String spitOutPath) {
    try (ManyFileReader reader = new ManyFileReader(path);
         AbstractManyFileWrite write =
            ManyFileWrite.manyFileWriteBySize(spitOutPath, UNIQUE_DATA_OUT_PATH)) {

      reader.openFile();
      write.openFile();

      String line;
      while ((line = reader.readLine()) != null) {
        write.writeLine(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

```



### 总结:

对于大型的多文件来说，读取并不复杂，重点在于文件名的排序、当前读取的文件索引，单文件读取结束时的文件切换，以及不要忘了实现AutoCloseable接口，只要做到这几点，多文件的连续读取就如同单文件一般顺滑。

对于大型的多文件写入来说，写入的逻辑同单文件，这个控制的重点在于，文件索引名中的索引号控制，单文件最大上限的控制。同样了不忘了实现AutoCloseable接口，做到这几点，多文件的连续写入，也如同写单文件一般。

如同我的实现一般。客户端在调用时，根本感知不到这是多文件的连续写入,感觉就是在使用JDK的IO流一样，只知道是在写文件，至于多大，何时做切换等一系的逻辑。客户端根本感知不到。







文件的切分就完成了，如果想查看更加详细的代码，可至我的github:

https://github.com/kkzfl22/datastruct/blob/master/src/main/java/com/liujun/datastruct/datacompare/bigfilecompare/uniquerows/UniqueRowProcess.java







下一节，我请继续分享超大文件对比的文件排序过程。