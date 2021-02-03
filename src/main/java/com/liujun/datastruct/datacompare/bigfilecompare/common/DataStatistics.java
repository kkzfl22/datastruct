package com.liujun.datastruct.datacompare.bigfilecompare.common;

/**
 * 数据统计
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataStatistics {

  /** 当前的数据 */
  private long dataValue;

  /** 开始时间 */
  private long startTime = System.currentTimeMillis();

  /** 区间开始时间 */
  private long scopeStartTime = System.currentTimeMillis();

  /** 增量信息 */
  private final long increment;

  /** 阈值 */
  private long threshold;

  public DataStatistics(long increment) {
    this.increment = increment;
    this.dataValue = 0;
    this.threshold = increment;
  }

  public void dataAdd(long value) {
    dataValue = dataValue + value;
    if (dataValue >= this.threshold) {
      long currTime = System.currentTimeMillis();
      System.out.println(
          "当前数据:"
              + dataValue
              + ",总用时:"
              + (currTime - startTime)
              + "，区间用时:"
              + (currTime - scopeStartTime));
      this.threshold = this.threshold + increment;
      scopeStartTime = System.currentTimeMillis();
    }
  }

  public long getDataValue() {
    return dataValue;
  }
}
