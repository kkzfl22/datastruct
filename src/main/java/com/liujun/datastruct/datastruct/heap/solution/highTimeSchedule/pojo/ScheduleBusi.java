package com.liujun.datastruct.datastruct.heap.solution.highTimeSchedule.pojo;

/**
 * 高性能的定时任务的实现实体
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class ScheduleBusi {

  /** 定时运行的时间点 */
  private long runTime;

  /** 定时任务的时间点 */
  private Runnable runable;

    public ScheduleBusi(long runTime, Runnable runable) {
        this.runTime = runTime;
        this.runable = runable;
    }

    public long getRunTime() {
    return runTime;
  }

  public void setRunTime(long runTime) {
    this.runTime = runTime;
  }

  public Runnable getRunable() {
    return runable;
  }

  public void setRunable(Runnable runable) {
    this.runable = runable;
  }
}
