package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 数据管理操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileOutputOperator {

  /** 文件名 */
  private final String filePath;

  /** 文件流程操作 */
  private FileWriter fileWriter;

  /** 文件流程对比 */
  private BufferedWriter bufferedWriter;

  /** 读取的文件流操作 */
  private FileReader fileReader;

  /** 读取流 */
  private BufferedReader bufferedReader;

  /**
   * 打注文件管理操作
   *
   * @param filePath 文件路径
   */
  public FileOutputOperator(String filePath) {
    this.filePath = filePath;
  }

  /** 打开写操作 */
  public void openWrite() {
    try {
      fileWriter = new FileWriter(this.filePath);
      bufferedWriter = new BufferedWriter(fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 打开文件读取操作
   *
   * @throws FileNotFoundException
   */
  public void openRead() {
    try {
      this.fileReader = new FileReader(this.filePath);
      this.bufferedReader = new BufferedReader(this.fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 文件流的写入
   *
   * @param data 数据信息
   * @throws IOException 异常
   */
  public void writeData(String data) {
    try {
      bufferedWriter.write(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 文件读取操作
   *
   * @throws IOException 异常操作
   */
  public String readData() {
    try {
      return this.bufferedReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /** 关闭文件写入操作 */
  public void closeWrite() {
    if (null != bufferedWriter) {
      IOUtils.close(bufferedWriter);
    }
    if (null != fileWriter) {
      IOUtils.close(fileWriter);
    }
  }

  /** 关闭文件读取 */
  public void closeReader() {
    if (null != this.bufferedReader) {
      IOUtils.close(this.bufferedReader);
    }

    if (null != this.fileReader) {
      IOUtils.close(this.fileReader);
    }
  }
}
