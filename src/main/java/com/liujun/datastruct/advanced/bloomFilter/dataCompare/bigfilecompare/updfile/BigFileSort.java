package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 大文件的排序操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileSort<V> {

  /** 以32M的单位的数据切分 */
  private static final long ONE_FILE_SIZE = 32 * 1024 * 1024;

  /** 文件转换接口 */
  private final DataParseInf<V> dataParse;

  /** 修改输出的文件夹名称 */
  private static final String UPD_DIR_NAME = "upd-data";

  /** 修改文件后缀名 */
  private static final String FILENAME = "update-data-";

  /** 对比临时文件后缀名 */
  private static final String SUFFIX_NAME = ".data";

  /** 文件路径 */
  private final String readPath;

  /** 当前修改的文件 */
  private File[] dataFile;

  /** 当前文件读取的索引 */
  private int readerIndex;

  /** 文件读取流 */
  private FileReader reader;

  /** 文件读取流 */
  private BufferedReader bufferedReader;

  /** 写入对象信息 */
  private FileWriter write;

  /** 带缓冲区的数据写入 */
  private BufferedWriter bufferedWriter;

  /** 当前写入文件编号索引 */
  private int writeIndex;

  /** 单文件大小 */
  private final Long maxOneFileSize;

  public BigFileSort(String readPath, DataParseInf<V> dataParse) {
    this(readPath, ONE_FILE_SIZE, dataParse);
  }

  public BigFileSort(String readPath, long maxOneFileSize, DataParseInf<V> dataParse) {
    this.readPath = readPath;
    dataFile = FileUtils.getFileList(this.readPath);
    if (dataFile.length == 0) {
      throw new IllegalArgumentException("path error, file not exists");
    }
    // 从0开始遍历文件
    this.writeIndex = 0;
    this.maxOneFileSize = maxOneFileSize;
    this.readerIndex = 0;
    this.dataParse = dataParse;
  }

  /**
   * 文件排序操作
   *
   * @return 文件输出的路径
   */
  public String bigFileSort() {
    // 1,将文件分片
    this.fileSpit();
    // 2,对每个文件执行排序操作
    this.fileSort();

    return this.getWritePath();
  }

  /** 文件切片操作 */
  public void fileSpit() {
    long readOneFile = 0;
    String readData = null;
    try {
      // 首先做文件目录的检查
      FileUtils.checkAndMakeDir(this.getWritePath());
      // 打开文件读取
      this.openReadFile();
      // 首先需要需要手动打开文件流的写入
      this.openWriteFile();

      while (true) {
        // 文件读取及写入操作
        while ((readData = bufferedReader.readLine()) != null) {
          int lineSize = readData.getBytes().length;

          // 检查是否超过了规定的大小,如果超过，则切换到下一个文件
          if (readOneFile + lineSize > maxOneFileSize) {
            nextWriteFile();
            readOneFile = 0;
          }
          readOneFile += lineSize;

          // 将内容写入至文件中
          this.bufferedWriter.write(readData + Symbol.LINE);
        }
        // 当前文件读取完成后切换到一下读取文件
        boolean readNextFlag = this.nextReaderFile();

        // 当切换失败，则说明文件已经结束,退出遍历
        if (!readNextFlag) {
          break;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 结束后需要关闭文件流
      this.closeWriteFile();
    }
  }

  /** 执行文件排序操作 */
  private void fileSort() {
    // 文件列表
    File[] dataList = FileUtils.getFileList(this.getWritePath());
    // 对文件执行排序
    for (File fileItem : dataList) {
      // 将文件转换为集合
      List<V> oneFileList = this.fileToList(fileItem);
      // 将集合排序
      QuickSort.quickSortList(oneFileList);
      // 再输出至文件中
      this.listToFile(oneFileList, fileItem);
    }
  }

  /**
   * 将文件转换为集合
   *
   * @param dataFile 数据信息
   * @return 结果信息
   */
  private List<V> fileToList(File dataFile) {
    List<V> dataList = new ArrayList<>();
    try (FileReader reader = new FileReader(dataFile);
        BufferedReader bufferedReader = new BufferedReader(reader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        dataList.add(dataParse.lineToData(line));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataList;
  }

  /**
   * 将排序后的文件输出到文件中
   *
   * @param data 排序后的数据
   * @param outFile 文件信息
   */
  private void listToFile(List<V> data, File outFile) {
    try (FileWriter write = new FileWriter(outFile);
        BufferedWriter bufferedWriter = new BufferedWriter(write)) {
      for (V item : data) {
        bufferedWriter.write(dataParse.toFileLine(item));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 打开文件读取 */
  private void openReadFile() {
    try {
      this.reader = new FileReader(this.dataFile[this.readerIndex]);
      this.bufferedReader = new BufferedReader(this.reader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 切换到下一个读取文件
   *
   * @return true 切换文件成功 false 切换失败
   */
  public boolean nextReaderFile() {
    // 关闭上一个文件读取操作
    this.closeReaderFile();
    // 切换到下一个文件
    this.readerIndex++;

    if (this.readerIndex < dataFile.length) {
      // 打开新文件
      this.openReadFile();
      return true;
    }
    return false;
  }

  /** 关闭读取文件 */
  private void closeReaderFile() {
    if (this.bufferedReader != null) {
      IOUtils.close(this.bufferedReader);
    }
    if (this.reader != null) {
      IOUtils.close(this.reader);
    }
  }

  /** 打开写入文件 */
  private void openWriteFile() {
    try {
      this.write = new FileWriter(this.getWriteFileName());
      this.bufferedWriter = new BufferedWriter(this.write);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 下一个写入文件 */
  private void nextWriteFile() {
    // 关闭当前文件写入
    this.closeWriteFile();
    // 切换到下一个索引
    this.writeIndex++;
    // 打羡慕新文件
    this.openWriteFile();
  }

  /** 关闭写入文件 */
  public void closeWriteFile() {
    if (null != bufferedWriter) {
      IOUtils.close(this.bufferedWriter);
    }
    if (null != write) {
      IOUtils.close(this.write);
    }
  }

  /**
   * 获取写文件路径
   *
   * @return
   */
  private String getWriteFileName() {
    StringBuilder outFileName = new StringBuilder();

    outFileName.append(this.getWritePath());
    outFileName.append(FILENAME);
    outFileName.append(writeIndex);
    outFileName.append(SUFFIX_NAME);

    return outFileName.toString();
  }

  /**
   * 获取写入的路径
   *
   * @return 获取写入路径
   */
  public String getWritePath() {
    StringBuilder outFilePath = new StringBuilder();
    outFilePath.append(this.readPath);
    outFilePath.append(Symbol.PATH);
    outFilePath.append(UPD_DIR_NAME);
    outFilePath.append(Symbol.PATH);
    return outFilePath.toString();
  }
}
