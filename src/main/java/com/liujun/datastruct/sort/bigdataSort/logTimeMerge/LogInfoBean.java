package com.liujun.datastruct.sort.bigdataSort.logTimeMerge;

/**
 * 日志信息
 *
 * <p>日志格式
 *
 * <p>[DEBUG] [2017-10-18 00:47:21,052]
 * [org.springframework.core.env.AbstractEnvironment.<init>(AbstractEnvironment.java:114)] [main]
 * [868 ] : Initializing new StandardEnvironment
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class LogInfoBean implements Comparable<LogInfoBean> {

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

  @Override
  public int compareTo(LogInfoBean o) {
    if (this.getTimeCurr() > o.getTimeCurr()) {
      return 1;
    } else if (this.getTimeCurr() < o.getTimeCurr()) {
      return -1;
    }

    return 0;
  }
}
