package com.liujun.datastruct.datastruct.heap.solution.margeSmailFile.pojo;

import java.io.FileOutputStream;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/30
 */
public class OutFileBusi {

  /** 文件写入的缓冲区 */
  private int outIndex;

  /** 最大允许的缓冲区 */
  private int maxIndex;

  /** 缓冲区大小 */
  private byte[] buffer;

  /** 文件输出路径 */
  private String outPath;

  /** 输出流对象信息 */
  private FileOutputStream outputStream;

  public OutFileBusi(int maxIndex) {
    this.maxIndex = maxIndex;
    buffer = new byte[maxIndex];
  }

  public int getOutIndex() {
    return outIndex;
  }

  public void setOutIndex(int outIndex) {
    this.outIndex = outIndex;
  }

  public int getMaxIndex() {
    return maxIndex;
  }

  public void setMaxIndex(int maxIndex) {
    this.maxIndex = maxIndex;
  }

  public byte[] getBuffer() {
    return buffer;
  }

  public void setBuffer(byte[] buffer) {
    this.buffer = buffer;
  }

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

  public FileOutputStream getOutputStream() {
    return outputStream;
  }

  public void setOutputStream(FileOutputStream outputStream) {
    this.outputStream = outputStream;
  }
}
