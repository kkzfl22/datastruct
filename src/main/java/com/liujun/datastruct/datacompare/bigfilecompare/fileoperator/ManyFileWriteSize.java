package com.liujun.datastruct.datacompare.bigfilecompare.fileoperator;

import java.util.concurrent.atomic.AtomicLong;

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
