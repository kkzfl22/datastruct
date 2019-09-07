package com.liujun.datastruct.base.datastruct.heap.solution.highTimeSchedule;

import com.liujun.datastruct.base.datastruct.heap.solution.highTimeSchedule.pojo.ScheduleBusi;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class MySchedule implements Runnable {

  public static final MySchedule INSTANCE = new MySchedule();

  /** 使用小顶堆来存储任务队列 */
  private PriorityBlockingQueue<ScheduleBusi> queue =
      new PriorityBlockingQueue<>(
          8,
          (o1, o2) -> {
            if (o1.getRunTime() > o2.getRunTime()) {
              return 1;
            } else if (o1.getRunTime() < o2.getRunTime()) {
              return -1;
            }
            return 0;
          });

  public void delayRun(long time, Runnable run) {

    // 将任务加入队列
    queue.offer(new ScheduleBusi(time, run));
  }

  @Override
  public void run() {

    while (true) {

      // 从任务中获取第一时间，然后当前定时器设置在runtime后唤醒进行执行
      ScheduleBusi schedQueue = null;
      try {
        schedQueue = queue.poll(2, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println(schedQueue);

      if (schedQueue == null) {
        continue;
      }

      long currTime = System.currentTimeMillis();

      long runTime = schedQueue.getRunTime() - currTime;

      if (runTime > 0) {
        try {
          Thread.sleep(runTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      RunSchedule.INSTANCE.runTask(schedQueue.getRunTime(), schedQueue.getRunable());
    }
  }
}
