package com.liujun.datastruct.base.sort.bigdataSort.bucketSort.logTimeMerge;

/**
 * 文件的时间范围
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class FileMergeScope {

  /** 开始时间 */
  private long startTime;

  /** 结束时间 */
  private long endTime;

  /** 时间差 */
  private long diffTime;


  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public long getDiffTime() {

    return diffTime;
  }

  public void setDiffTime(long diffTime) {
    this.diffTime = diffTime;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FileMergeScope{");
    sb.append("startTime=").append(startTime);
    sb.append(", endTime=").append(endTime);
    sb.append(", diffTime=").append(diffTime);
    sb.append('}');
    return sb.toString();
  }
}
