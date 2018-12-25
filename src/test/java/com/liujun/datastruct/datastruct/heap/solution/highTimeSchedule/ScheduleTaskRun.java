package com.liujun.datastruct.datastruct.heap.solution.highTimeSchedule;

/**
 * 高性能定时任务的执行
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class ScheduleTaskRun {

  public static void main(String[] args) {

    // 30秒后执行当前任务
    long runTime = System.currentTimeMillis() + 1 * 10 * 1000;

    Runnable runTask =
        () -> {
          System.out.println("当前任务被执行");
          System.out.println("时间点为:" + System.currentTimeMillis());
        };

    MySchedule.INSTANCE.delayRun(runTime, runTask);
  }
}
