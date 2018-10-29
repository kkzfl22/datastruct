package com.liujun.sort.bigdataSort.logTimeMerge;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 桶文件信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/33
 */
public class BucketFileInfo extends FileMergeScope {

  /** 输出的文件信息 */
  private String fileOutInfo;

  /** 文件的索引编号 */
  private int index;

  /** 文件 */
  private FileWriter write;

  /** 缓冲文件流 */
  private BufferedWriter bufferWrite;

  public String getFileOutInfo() {
    return fileOutInfo;
  }

  public void setFileOutInfo(String fileOutInfo) {
    this.fileOutInfo = fileOutInfo;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public FileWriter getWrite() {
    return write;
  }

  public void setWrite(FileWriter write) {
    this.write = write;
  }

  public BufferedWriter getBufferWrite() {
    return bufferWrite;
  }

  public void setBufferWrite(BufferedWriter bufferWrite) {
    this.bufferWrite = bufferWrite;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BucketFileInfo{");
    sb.append("super='").append(super.toString()).append('\'');
    sb.append("fileOutInfo='").append(fileOutInfo).append('\'');
    sb.append(", index=").append(index);
    sb.append('}');
    return sb.toString();
  }
}
