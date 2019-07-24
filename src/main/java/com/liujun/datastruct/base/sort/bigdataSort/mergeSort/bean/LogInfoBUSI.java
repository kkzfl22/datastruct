package com.liujun.datastruct.base.sort.bigdataSort.mergeSort.bean;

/**
 * 日志信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class LogInfoBUSI implements Comparable<LogInfoBUSI> {

  /** 当前文件索引号 */
  private int fileIndex;

  /** 时间戳信息 */
  private long timeCurr;

  /** 日志中其他的信息 */
  private String loginfo;

  public long getTimeCurr() {
    return timeCurr;
  }

  public void setTimeCurr(long timeCurr) {
    this.timeCurr = timeCurr;
  }

  public String getLoginfo() {
    return loginfo;
  }

  public void setLoginfo(String loginfo) {
    this.loginfo = loginfo;
  }

  public int getFileIndex() {
    return fileIndex;
  }

  public void setFileIndex(int fileIndex) {
    this.fileIndex = fileIndex;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LogInfoBUSI{");
    sb.append("fileIndex=").append(fileIndex);
    sb.append(", timeCurr=").append(timeCurr);
    sb.append(", loginfo='").append(loginfo).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int compareTo(LogInfoBUSI o) {
    if (this.getTimeCurr() > o.getTimeCurr()) {
      return 1;
    } else if (this.getTimeCurr() < o.getTimeCurr()) {
      return -1;
    }

    return 0;
  }
}
