package com.liujun.datastruct.base.datastruct.heap.solution.highTimeSchedule;

import java.time.LocalDateTime;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class RunSchedule {

  public static final RunSchedule INSTANCE = new RunSchedule();

  public final long getCurrTime() {
    return System.currentTimeMillis();
  }

  public void runTask(long runtime, Runnable task) {

    long currTime;

    while (true) {
      currTime = this.getCurrTime();
      long needTime = runtime - currTime;

      if (needTime > 0) {
        try {
          Thread.sleep(needTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } else {
        break;
      }
    }

    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println("执行时间:"+localDateTime.toString());

    task.run();
  }
}
