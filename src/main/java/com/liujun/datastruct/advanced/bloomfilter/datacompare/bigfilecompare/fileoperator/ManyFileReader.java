package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator;

import com.config.Symbol;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 多文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileReader implements AutoCloseable {

  /** 每次读取512条记录 */
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
