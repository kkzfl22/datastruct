package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 处理etl的调度任务处理
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/31
 */
public class ScheduleTaskThreadPool {

  /**
   * 线程池中的核心线程数，当提交一个任务时，线程池创建一个新线程执行任务，直到当前线程数等于corePoolSize；
   * 如果当前线程数为corePoolSize，继续提交的任务被保存到阻塞队列中，等待被执行；
   * 如果执行了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有核心线程。
   */
  private static final int minThread = 2;

  /**
   * 线程池中允许的最大线程数。如果当前阻塞队列满了，且继续提交任务，则创建新的线程执行任务，
   *
   * <p>前提是当前线程数小于maximumPoolSize
   */
  private static final int maxThread = 8;

  /**
   * 线程池中允许的最大线程数。如果当前阻塞队列满了，且继续提交任务，则创建新的线程执行任务，
   *
   * <p>前提是当前线程数小于maximumPoolSize
   */
  private static final int WAIT_NUM = 16;

  /**
   * 线程空闲时的存活时间，即当线程没有任务执行时，继续存活的时间。以纳秒为单位
   *
   * <p>默认情况下，该参数只在线程数大于corePoolSize时才有用
   */
  private static final int keepAlive = 32;

  /** 进行任务调度的线程池对象 */
  public static final ScheduleTaskThreadPool INSTANCE = new ScheduleTaskThreadPool();

  /**
   * workQueue必须是BlockingQueue阻塞队列。当线程池中的线程数超过它的corePoolSize的时候，
   *
   * <p>线程会进入阻塞队列进行阻塞等待。 通过workQueue，线程池实现了阻塞功能 1,（1）不排队，直接提交 将任务直接交给线程处理而不保持它们，可使用SynchronousQueue
   * 如果不存在可用于立即运行任务的线程（即线程池中的线程都在工作），则试图把任务加入缓冲队列将会失败，
   * 因此会构造一个新的线程来处理新添加的任务，并将其加入到线程池中（corePoolSize-->maximumPoolSize扩容）
   * Executors.newCachedThreadPool()采用的便是这种策略
   *
   * <p>（2）无界队列 可以使用LinkedBlockingQueue（基于链表的有界队列，FIFO），理论上是该队列可以对无限多的任务排队
   * 将导致在所有corePoolSize线程都工作的情况下将新任务加入到队列中。这样， 创建的线程就不会超过corePoolSize，也因此，maximumPoolSize的值也就无效了
   *
   * <p>（3）有界队列 可以使用ArrayBlockingQueue（基于数组结构的有界队列，FIFO），并指定队列的最大长度
   * 使用有界队列可以防止资源耗尽，但也会造成超过队列大小和maximumPoolSize后，提交的任务被拒绝的问题，
   *
   * <p>比较难调整和控制。
   *
   * <p>等待任务的队列
   */
  private ArrayBlockingQueue queue = new ArrayBlockingQueue(WAIT_NUM);

  /** 创建线程的工厂，通过自定义的线程工厂可以给每个新建的线程设置一个具有识别度的线程名 */
  private ThreadFactory factory = new ScheduleTaskThreadFactory();

  /**
   * 策略说明 1,ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
   *
   * <p>2,ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
   *
   * <p>3,ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
   *
   * <p>4,ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务,这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
   */
  private ThreadPoolExecutor pool =
      new ThreadPoolExecutor(
          minThread,
          maxThread,
          keepAlive,
          TimeUnit.SECONDS,
          queue,
          factory,
          new ThreadPoolExecutor.CallerRunsPolicy());

  /**
   * 提交任务给线程池运行
   *
   * @param task
   */
  public Future<?> submit(Runnable task) {
    return pool.submit(task);
  }

  public void shutdown() {
    pool.shutdown();
  }
}
