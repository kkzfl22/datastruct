package com.liujun.datastruct.datacompare.bigfilecompare.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 当前的任务扩容策略。任务优先启动进程
 *
 * @author liujun
 * @version 0.0.1
 */
public class AddRunTaskRejectedHandler implements RejectedExecutionHandler {

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

      //检查当前的任务是否已经满载
      if(executor.getPoolSize() == executor.getMaximumPoolSize())
      {

      }

  }
}
