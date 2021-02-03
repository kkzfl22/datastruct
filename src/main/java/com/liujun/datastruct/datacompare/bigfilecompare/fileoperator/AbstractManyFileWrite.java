package com.liujun.datastruct.datacompare.bigfilecompare.fileoperator;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.constant.CompareConfig;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

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
