package com.liujun.datastruct.base.datastruct.heap.solution.highTimeSchedule;

import java.time.LocalDateTime;

/**
 * 高性能定时任务的执行
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class ScheduleTaskRun {

  public static void main(String[] args) {

    Thread thread = new Thread(MySchedule.INSTANCE);

    thread.start();

    // 5秒后执行当前任务
    long runTime = System.currentTimeMillis() + 1 * 5 * 1000;

    Runnable runTask =
        () -> {
          System.out.println("当前任务被执行");
          System.out.println("时间点为:" + System.currentTimeMillis());
        };

    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println("启动时间:" + localDateTime.toString());

    MySchedule.INSTANCE.delayRun(runTime, runTask);
    runTime = System.currentTimeMillis() + 1 * 8 * 1000;
    MySchedule.INSTANCE.delayRun(runTime, runTask);
    runTime = System.currentTimeMillis() + 1 * 7 * 1000;
    MySchedule.INSTANCE.delayRun(runTime, runTask);
  }
}
