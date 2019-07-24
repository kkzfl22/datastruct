package com.liujun.datastruct.base.sort.bigdataSort.mergeSort.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 有序小文件信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class SortFileBUSI {

  /** 文件路径信息 */
  private String filePath;

  /** 文件位置信息 */
  private long filePosition;

  /** 文件内容行信息 */
  private List<LogInfoBUSI> dataLine = new ArrayList<>(8);

  /** 在数据中的位置 */
  private int fileIndex;

  /** 读取完成标识 */
  private boolean readFinish;

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public long getFilePosition() {
    return filePosition;
  }

  public void setFilePosition(long filePosition) {
    this.filePosition = filePosition;
  }

  public List<LogInfoBUSI> getDataLine() {
    return dataLine;
  }

  public void setDataLine(List<LogInfoBUSI> dataLine) {
    this.dataLine = dataLine;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  public boolean isReadFinish() {
    return readFinish;
  }

  public void setReadFinish(boolean readFinish) {
    this.readFinish = readFinish;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SortFileBUSI{");
    sb.append("filePath='").append(filePath).append('\'');
    sb.append(", filePosition=").append(filePosition);
    sb.append(", dataLine=").append(dataLine);
    sb.append(", fileIndex=").append(fileIndex);
    sb.append(", readFinish=").append(readFinish);
    sb.append('}');
    return sb.toString();
  }
}
