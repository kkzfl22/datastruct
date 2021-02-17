package com.liujun.datastruct.datacompare.bigfilecompare.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的默认策略中当核心任务满了后，将任务放入队列，当队列满了，启动最大线程，当达到最大线程池后，任务也满了，执行拒绝策略。
 *
 * <p>自定义线程池，采用激进的策略，首先任务还是启动核心线程，当线程满了之后，将启动最大线程，当最大线程也在运行了，其他任务则加入到队列中，当队列也满了执行拒绝策略
 *
 * @author liujun
 * @version 0.0.1
 */
public class TaskThreadDataPool extends ThreadPoolExecutor {

  /**
   * 自定义激动线程池参数
   *
   * @param corePoolSize 核心线程数
   * @param maximumPoolSize 最大线程数
   * @param keepAliveTime 线程存活的时间
   * @param unit 时间单位
   * @param queueSize 队列大小
   * @param threadFactory 线程工厂
   */
  public TaskThreadDataPool(
      int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      int queueSize,
      ThreadFactory threadFactory) {
    super(
        corePoolSize,
        maximumPoolSize,
        keepAliveTime,
        unit,
        new ExtractBlockQueue(queueSize),
        threadFactory,
        new ExtractHandler());
  }

  /** 修改默认的队列，以增加拒绝策略的相关实现 */
  static class ExtractBlockQueue extends LinkedBlockingQueue<Runnable> {
    public ExtractBlockQueue(int capacity) {
      super(capacity);
    }

    /**
     * 修改默认的拒绝策略的行为，直接返回入队列失败，触发拒绝策略执行
     *
     * @param runnable
     * @return
     */
    @Override
    public boolean offer(Runnable runnable) {
      return false;
    }

    /**
     * 拒绝策略触发后，将任务真正的保存到队列中
     *
     * @param runTask 运行的任务信息
     * @return
     */
    public boolean extractOffer(Runnable runTask) {
      return super.offer(runTask);
    }
  }

  /** 拒绝策略的触发 */
  static class ExtractHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      // 1,检查队列是否关闭
      if (executor.isShutdown()) {
        return;
      }

      // 执行线程池的加入操作
      if (!((ExtractBlockQueue) executor.getQueue()).extractOffer(r)) {
        // 当加入队列失败说明，任务已经满载，不能再加入任务，必须执行拒绝策略了
        throw new RejectedExecutionException(
            "Task " + r.toString() + " rejected from " + executor.toString());
      }
    }
  }
}
