package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileReader {

  /** 基础的文件路径 */
  private final String basePath;

  /** 当前的文件列表 */
  private File[] listFile;

  /** 最大的索引 */
  private int readMaxIndex;

  /** 读取的文件索引 */
  private int readIndex;

  /** 读取流操作 */
  private Reader reader;

  /** 缓冲流操作 */
  private BufferedReader bufferedReader;

  /** 读取结束标识 */
  private boolean readerFinish = false;

  public ManyFileReader(String basePath) {
    this.basePath = basePath;
    readIndex = 0;
    this.listFile = FileUtils.getFileList(this.basePath);
    if (this.listFile.length < 1) {
      throw new IllegalArgumentException("file not exists");
    }
    this.readMaxIndex = this.listFile.length;
  }

  /** 打开流操作 */
  public boolean open() {
    if (readIndex >= this.readMaxIndex) {
      return false;
    }
    try {
      reader = new FileReader(listFile[readIndex]);
      bufferedReader = new BufferedReader(reader);
      return true;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * * 读取多行数据
   *
   * @return
   */
  public List<String> readLineMany(int size) {

    // 读取文件读取结束后将不在进行读取操作
    if (readerFinish) {
      return Collections.emptyList();
    }

    List<String> dataList = new ArrayList<>(size);

    try {
      String line = null;
      for (int i = 0; i < size; i++) {
        line = this.bufferedReader.readLine();
        // 说明文件读取结束，读取下一个
        if (line == null) {
          // 1.关闭上一个文件
          this.close();
          // 切换文件索引到下一个文件
          boolean nextFlag = nextFile();
          // 切换失败，则退出
          if (!nextFlag) {
            readerFinish = true;
            break;
          }
          this.open();

          // 重新打开后需要再次读取一次数据才能加载到
          line = this.bufferedReader.readLine();
        }
        dataList.add(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataList;
  }

  /** 执行关闭操作 */
  public void close() {
    if (null != bufferedReader) {
      IOUtils.close(bufferedReader);
    }
    if (null != reader) {
      IOUtils.close(reader);
    }
  }

  /** 重新设置标志位开始读取操作 */
  public void reload() {
    this.readIndex = 0;
    this.readerFinish = false;
  }

  /**
   * 切换到下一个文件
   *
   * @return true 切换成功 false 切换失败，读取已经结束
   */
  public boolean nextFile() {
    readIndex++;

    if (readIndex >= readMaxIndex) {
      return false;
    } else {
      return true;
    }
  }
}
