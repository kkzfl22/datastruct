package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile;

import com.config.Symbol;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 多文件写入操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileWrite implements AutoCloseable {

  /** 文件写入标识 */
  private static final String WRITE_ING_FLAG = ".ing";

  /** 写入的文件后缀名 */
  private static final String WRITE_FILE_SUFFIX_NAME = ".txt";

  /** 默认的文件大小32M */
  private static final long DEFAULT_FILE_SIZE = 32 * 1024 * 1024;

  /** 基础的文件路径 */
  private final String path;

  /** 输出的文件名 */
  private final String fileName;

  /** 当前写入的编写 */
  private int writeIndex;

  /** 文件写入 */
  private FileWriter writer;

  /** 带缓冲区的文件写入 */
  private BufferedWriter bufferedWriter;

  /** 切分的文件大小 */
  private final long spitFileSize;

  /** 当前文件大小 */
  private long fileSize;

  /** 总写入量 */
  private long totalFileSize;

  /**
   * 多路文件写入,按默认的32M的文件进行写入
   *
   * @param path 文件路径
   * @param fileName 文件名称
   */
  public ManyFileWrite(String path, String fileName) {
    this(path, fileName, DEFAULT_FILE_SIZE);
  }

  /**
   * 多路文件写入
   *
   * @param path 文件路径
   * @param fileName 文件名称
   * @param spitFileSize 单文件大小
   */
  public ManyFileWrite(String path, String fileName, long spitFileSize) {
    this.path = path;
    this.writeIndex = 0;
    this.fileName = fileName;
    this.spitFileSize = spitFileSize;
    this.fileSize = 0;
  }

  /** 文件打开操作 */
  public void open() {
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
   * @param data 数据内容
   */
  public void writeLine(String data) throws IOException {
    // 1,计算文件大小
    int dataLength = data.getBytes().length;
    // 当文件大于了指定大小后，需进行分隔
    if (this.fileSize + dataLength > spitFileSize) {
      // 切换到下一个文件
      nextFile();
    }
    this.bufferedWriter.write(data + Symbol.LINE);
    this.fileSize = this.fileSize + dataLength;
    this.totalFileSize = this.totalFileSize + dataLength;
  }

  /** 切换到下一个文件 */
  private void nextFile() {
    // 关闭当前文件
    this.close();
    // 执行文件重命名
    FileUtils.rename(getFileNameWritePath(), getFileName());
    // 切换到下一文件
    this.writeIndex++;
    // 打开新文件
    this.open();
    // 将当前文件大写置为0
    this.fileSize = 0;
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
    fileNameOut.append(writeIndex);
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
