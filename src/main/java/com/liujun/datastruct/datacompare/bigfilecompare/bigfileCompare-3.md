# 在单台机器上实现TB级的数据对比之数据合并

## 整体思路

在上一章节，我介绍了如何对切分后的数据进行并行排序，这节来到了最后的操作，数据的合并操作。将数据进行对比，找出添加的数据、修改的数据、删除的数据。需要特别处理下重复的数据。在第一章节我已经介绍了这个大致的思路，但未深入细节来讨论如何实现，这章将深入细节将数据合并的逻辑做一个完整的说明。

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并逻辑.png)

这个逻辑看着有点复杂吧，那我来详细的解释下吧！

1. 在原始排序文件中读取所有文件的首行数据，并记录到以文件索引为下标的数组中。就叫它srcDataList

2. 在目标排序文件中读取所有文件的首行数据。并记录到以文件索引为下标识的数组中。就叫它targetDataList

3. 开始遍历执行。

4. 在srcDataList找到最小的记录，记为srcMinData。

5. 在targetDataList找到最小的记录，记为targetMinData。

6. 这时就要开始对srcMinData和targetMinData进行操作.一共会出现4种情况.我将分别对它们进行说明

   1. 情况1：srcMinData与targetMinData都为空。那什么时候这两个都会为空呢！当然是数据已经读取完毕了。这种情况就意味着要退出对比了。

   2. 情况2：srcMinData与targetMinData都不为空。这时候说明两边都读取到了数据，就数据做数据检查了。

      1. srcMinData与targetMinData主键相同，这个情况说明两边的主键是一致的。可能为修改操作。具体是否修改操作。还需要做全量的数据对比才能确定。所以接下来就是做srcMinData与targetMinData全量数据对比。这样子就会出现两种情况。

         1. srcMinData与targetMinData全量数据后，全量数据一致。说明数据没有任何改变，这个数据可直接放弃。除非有些情况需要这部分数据，做一个输出到文件的操作。像本题中，就是不需要的，直接抛弃。
         2. srcMinData与targetMinData全量数据后。全量数据不一致。说明数据有改动。这时候就需要将数据记录到修改文件中。

         接下来就是读取原始文件中最小的数据行所在的文件的下一行。再读取目标文件中最小的数据行所在的文件的下一行。当前遍历结束，继续下一轮遍历。

      2. srcMinData与targetMinData主键不相同，这个时候就说明数据出现了错位。需要将srcMinData与targetMinData执行比较。将会出现两种情况，即srcMinData大于targetMinData，或者srcMinData小于targetMinData，难道就没有等于了吗? 这个确实就没有了，因为是优先做的主键对比。主键都不一致，不可能出现相等的情况了。

         1. srcMinData小于targetMinData，说明数据在原始数据中存在，目标中不存在，需执行删除，写入删除文件中。然后读取原始文件中最小的数据行所在的文件的下一行，当前遍历结束，继续下一轮遍历。
         2. srcMinData大于targetMinData，说明数据在原始数据中不存在，在目标中存在，需执行添加，写入添加文件中。然后读取目标文件中最小的数据行所在的文件的下一行。当前遍历结束，继续下一轮遍历。

   3. 情况3：srcMinData为空，targetMinData不为空，这种情况说明原始数据已经读取结束了，只留下了目标数据，只有目标数据时，即为所有都是添加数据，记录到添加文件即可。然后读取目标文件中最小的数据行所在的文件的下一行。当前遍历结束，继续下一轮遍历。

   4. 情况4：srcMinData不为空，targetMinData为空，这种情况说明目标数据已经读取结束了，中留下了原始数据，只有原始数据。即所有都是待删除的数据。记录到删除文件即可。然后读取原始文件中的最小的数据行所有的文件的下一行。当前遍历结束，继续下一轮遍历。



这就是数据对比的详细逻辑。也没有那么复杂呢。

```java
 try ( // 原始文件读取器
    MergeFileOperatorPriorityQueue<V> mergeSrcFileOperator =
            new MergeFileOperatorPriorityQueue<>(dataEntity, srcPath, dataParse, compareKey);
        // 目标数据读取器
        MergeFileOperatorPriorityQueue<V> mergeTargetFileOperator =
            new MergeFileOperatorPriorityQueue<>(dataEntity, targetPath, dataParse, compareKey);
        // 输出操作
        DataCompareFileOutput output =
            new DataCompareFileOutput(inputEntity.getCompareOutPath()); ) {
      // 打开文件读取器
      mergeSrcFileOperator.open();
      mergeTargetFileOperator.open();
      // 首次数据加载
      mergeSrcFileOperator.firstLoader();
      mergeTargetFileOperator.firstLoader();

      // 打开文件写入器
      output.openWriteFile();

      // 得到原始中最小的
      V srcMinData = mergeSrcFileOperator.readerMin();
      // 得到目标中最小的
      V targetMinData = mergeTargetFileOperator.readerMin();

      DataCompareOutRsp dataRsp = new DataCompareOutRsp(DEFAULT_BATCH_SIZE);

      int index = 0;
      int threshold = DEFAULT_BATCH_SIZE;

      while (true) {
        // 当两个都为空时，做出退出操作
        if (srcMinData == null && targetMinData == null) {
          break;
        }
        // 这是两个都不为空的情况
        else if (null != srcMinData && targetMinData != null) {
          String srcKey = compareKey.getKey(srcMinData);
          String targetKey = compareKey.getKey(targetMinData);

          // 如果数据key相同，说明可能是修改，则进一步检查数据值是否相同，
          if (srcKey.equals(targetKey)) {
            // 检查数据是否完全一致
            String srcFullDataKey = compareKey.getKeyMany(srcMinData);
            String targetFullDataKey = compareKey.getKeyMany(targetMinData);

            // 当数据不一至，则写入至文件中
            if (!srcFullDataKey.equals(targetFullDataKey)) {
              String dataSrcFull1 = dataParse.toFileLine(srcMinData);
              String dataTargetFull = dataParse.toFileLine(targetMinData);
              dataRsp
                  .getUpdateList()
                  .add(
                      dataSrcFull1
                          + Symbol.MINUS
                          + Symbol.MINUS
                          + Symbol.RIGHT_FLAG
                          + dataTargetFull);
            }

            // 因为key相同，则读取下一行
            srcMinData = mergeSrcFileOperator.readerMin();
            targetMinData = mergeTargetFileOperator.readerMin();
          }
          // 如果不同，则进说明可能在添加或者是删除
          else {
            // 数据比较后的结果
            int compareRsp = ((Comparable) srcMinData).compareTo(targetMinData);
            // 说明原始数据，小于目标数据,则为删除操作,
            // 解释下为什么 是删除操作，删除原始数据小于目标数据，这说明在原始数据中存在，在目标数据是不存在，这就是删除数据
            // 完成添加后，将原始数据写入至删除中，原始读取下一个
            if (compareRsp < 0) {
              dataRsp.getDeleteList().add(dataParse.toFileLine(srcMinData));
              srcMinData = mergeSrcFileOperator.readerMin();
            }
            // 如果原始数据大于目标数据，说明为添加操作，
            // 这明也解释下为什么是添加操作，原始数据大于目标数据，说明在原始中不存，在目标数据中存在，这就是添加数据
            // 写入完成后，需将目标数据写入至添加中，读取下一个
            else if (compareRsp > 0) {
              dataRsp.getAddList().add(dataParse.toFileLine(targetMinData));
              targetMinData = mergeTargetFileOperator.readerMin();
            }
          }
        }
        // 原始为空，目标不为空，则进行添加操作
        else if (srcMinData == null && targetMinData != null) {
          // output.writeAddData(dataParse.toFileLine(targetMinData));
          dataRsp.getAddList().add(dataParse.toFileLine(targetMinData));
          targetMinData = mergeTargetFileOperator.readerMin();
        }
        // 原始不为空，目标为空，则为删除操作
        else if (srcMinData != null && targetMinData == null) {
          // output.writeDeleteData(dataParse.toFileLine(srcMinData));
          dataRsp.getDeleteList().add(dataParse.toFileLine(srcMinData));
          srcMinData = mergeSrcFileOperator.readerMin();
        }
		//增加IO的效率，优先内存缓冲区，再写入磁盘。
        if (index >= threshold - 1) {
          // 执行数据的写入操作
          output.writeData(dataRsp);
          dataRsp.clean();
          index = 0;
        }
        index++;
      }

      // 完成后执行数据的写入操作
      output.writeData(dataRsp);
      dataRsp.clean();
      index = 0;

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
```





## 去重的问题解决方案

讲完了数据对比的逻辑，再来说下取数据的最小值，以及去重的问题。

先来看下存储结构吧！

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并文件结构.png)

当首行都加载到内存后数组后。就开始了取最小值及去重的操作。具体怎么做呢？

先说最小吧！这个算法太简单了，直接在填充数据中做个遍历就可以了，就能找到最小的数据项了。像本例中找到最小的值就是"5, 48108618651,1,2",也就是文件2中的首行数据。这个先称为临时最小吧。

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并文件结构-临时最小.png)

当我们找到临时最小数据后。接下来就是去重操作。具体来说就是在主键相同的数据内查找时间最近的记录，只保留最新的记录即可。

这个重复可以分为两个维度。一个维度是文件内主键key相同的记录，查找时间最近的记录。还有一个维度是多文件的首行记录，查找时间最近的记录。

具体呢，我是这样操作的。首先将临时最小值在填充数组内进行查找，找到key与当前key相同的记录。当key相同时，取时间戳最新的记录。

然后在单文件内一直找，找到key与当前最小的key不同为止.

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并文件结构-多文件比较.png)

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并文件结构-多文件取最最大.png)

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并文件结构-多文件取最大2.png)

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序合并-合并文件结构-多文件取最大3.png)

经过这样一个查找对比逻辑后，key相同的最大值也被找出来了。

最后来看看代码实现

```java
public class MergeFileOperator<V> implements AutoCloseable {

  /** 文件操作 */
  private final File[] files;

  /** 数据读取器操作 */
  private final FileDataReader<V>[] dataReader;

  /** 存储的数据 */
  private V[] dataList;

  /** 数据转换操作 */
  private final DataParseInf<V> dataParse;

  /** 对比的键信息 */
  private final BigCompareKeyInf<V> compareKey;

  public MergeFileOperator(
      Class<V> installClass,
      String filePath,
      DataParseInf<V> dataParse,
      BigCompareKeyInf<V> compareKey) {
    this.files = FileUtils.getFileList(filePath);
    this.dataReader = new FileDataReader[this.files.length];
    this.dataParse = dataParse;
    this.dataList = (V[]) Array.newInstance(installClass, this.files.length);
    this.compareKey = compareKey;
  }

  /** 读打开文件 */
  public void open() {
    for (int i = 0; i < files.length; i++) {
      dataReader[i] = new FileDataReader(dataParse, files[i]);
      dataReader[i].open();
    }
  }

  /**
   * 首次的全量加载
   *
   * @throws IOException
   */
  public void firstLoader() throws IOException {
    for (int i = 0; i < files.length; i++) {
      dataList[i] = dataReader[i].read();
    }
  }

  /**
   * 读取最小的一个数据，并将相同的数据进行过滤操作
   *
   * @return 最后一条记录，其他记录都被过滤
   * @throws IOException 异常信息
   */
  public V readerMin() throws IOException {
    V dataTmp = null;
    int minLast = -1;

    for (int i = 0; i < dataList.length; i++) {
      // 确保数据不为空
      if (null == dataList[i]) {
        continue;
      }
      // 第一次找到不为空的
      if (null != dataList[i] && minLast == -1) {
        minLast = i;
        dataTmp = dataList[i];
        continue;
      }

      // 查找最小值
      int compareRsp = ((Comparable) dataList[minLast]).compareTo(dataList[i]);
      if (compareRsp == 1) {
        dataTmp = dataList[i];
        minLast = i;

        continue;
      }
    }

    if (null != dataTmp) {
      dataTmp = findKeyMax(dataTmp);
    }
    return dataTmp;
  }

  /**
   * 在相同key中查找最大的值
   *
   * @param dataMinTemp 时间最大值
   * @throws IOException
   */
  private V findKeyMax(V dataMinTemp) throws IOException {
    V dataRspMax = dataMinTemp;
    String valueKey = compareKey.getKey(dataRspMax);

    for (int i = 0; i < dataList.length; i++) {

      // 当数据为空时，则跳过
      if (dataList[i] == null) {
        continue;
      }

      String currKey = compareKey.getKey(dataList[i]);
      if (currKey.equals(valueKey)) {
        V rspMaxTmp = this.nextOtherKey(i, valueKey, dataRspMax);
        // 如果查到的结果比当前大，则说设置当前为最大值
        if ((((Comparable) dataRspMax).compareTo(rspMaxTmp)) == -1) {
          dataRspMax = rspMaxTmp;
        }
      }
    }

    return dataRspMax;
  }

  /**
   * 单个结果 切换到下一个结果
   *
   * @param index
   * @param dataKey
   * @param compareData
   * @return
   * @throws IOException
   */
  private V nextOtherKey(int index, String dataKey, V compareData) throws IOException {
    V rspMax = compareData;
    // 文件需要切换到后一个数据
    while (true) {

      if (dataList[index] == null) {
        break;
      }
      // 优先对当前执行对比
      String key = compareKey.getKey(dataList[index]);
      // 仅找到不同的数据才，进行相关数据的退出操作
      if (!dataKey.equals(key)) {
        break;
      }

      // 对比的结果,找到比当前时间大的，则结果为当前值
      int compareRsp = ((Comparable) rspMax).compareTo(dataList[index]);
      if (compareRsp == -1) {
        rspMax = dataList[index];
      }

      // 读取下一条
      dataList[index] = dataReader[index].read();
      if (dataList[index] == null) {
        break;
      }
    }

    return rspMax;
  }

  @Override
  public void close() throws Exception {
    for (int i = 0; i < dataReader.length; i++) {
      IOUtils.close(dataReader[i]);
    }
  }
}
```



文件合并去重的逻辑至此就完成了



## 测试

这是在1亿数据量下的结果

![](D:\doc\博客\数据结构与算法\大型文件对比\1亿数据量笔记本测试.png)





## 总结

有序文件合并是这个章节中逻辑最复杂的操作了，这中间涉及，多文件读取、填充数组、查找最小值、取最新值去重的操作，及最后的输出。至此大型文件对比的方案就分享完了，对于我来说这中间也走了不少的弯路，也有很多的收获。收到最大的莫过于，对于这类超大型数据集处理，抛开hadoop这种平台，我能依靠自己所学的算法数据结构及文件处理将这些数据给处理掉。我想这是对我最大的鼓励了吧。我将所有的思考与解决方案都分享出来。希望对大家有所帮助。