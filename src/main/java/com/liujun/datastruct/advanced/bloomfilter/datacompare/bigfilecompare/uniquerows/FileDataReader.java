package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.uniquerows;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件数据读取
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileDataReader<V> implements AutoCloseable {

  /** 数据转换操作 */
  private final DataParseInf<V> dataParse;

  /** 文件信息 */
  private final File fileInput;

  /** 读取对象信息 */
  private FileReader reader;

  /** 缓冲区对象 */
  private BufferedReader bufferedReader;

  public FileDataReader(DataParseInf<V> dataParse, File fileInput) {
    this.dataParse = dataParse;
    this.fileInput = fileInput;
  }

  public void open() {
    try {
      this.reader = new FileReader(this.fileInput);
      this.bufferedReader = new BufferedReader(this.reader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 数据读取操作
   *
   * @return
   * @throws IOException
   */
  public V read() throws IOException {
    String dataValue = bufferedReader.readLine();
    if (null == dataValue) {
      return null;
    }
    return this.dataParse.lineToData(dataValue);
  }

  /**
   * 流的关闭操作
   *
   * @throws Exception
   */
  @Override
  public void close() throws Exception {
    if (null != bufferedReader) {
      IOUtils.close(this.bufferedReader);
    }
    if (null != reader) {
      IOUtils.close(this.reader);
    }
  }
}
