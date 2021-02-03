package com.liujun.datastruct.datacompare.bigfilecompare.threadpool;

import java.util.concurrent.ThreadFactory;

/**
 * 用来实现任务线程的执行任务
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/31
 */
public class ScheduleTaskThreadFactory implements ThreadFactory {

  private static final String PREFIX = "task-thread-";

  @Override
  public Thread newThread(Runnable r) {
    // 仅设置一个线程名称
    Thread currThread = new Thread(r, PREFIX);
    return currThread;
  }
}
