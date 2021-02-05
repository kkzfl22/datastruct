# 在单台机器上实现TB级的数据对比之数据排序

## 整体流程

在上一个章节已经实现了对大型文件的拆分.今天来继续分享关于数据的排序。

文件排序的逻辑不复杂。将数据读取进内存中然后排序。写入至磁盘中。由于这些文件相互完全不关联。可以并行化处理。那先还是看下整体的一个结构吧。

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序多文件并行排序.png)

这个主要涉及到以下内容:

1. 并行排序控制器，它的主要作用是控制向线程池中加入任务的数量。
2. 线程池，用来执行当前的排序任务。
3. 排序任务。将排序中涉及的到操作，封装到一个任务中。包括从文件中读取数据到内存，再将内存中的内容进行排序，最后再将排序后的内容输出到原文件中。



## 并行控制器

先来聊下为什么要有并行控制器。

这是由几个方面的原因才导致需要并行控制器。

1. 这个当然与线程池的拒绝策略有关了，线程池的一般策略为优先使用核心线程数；当核心线程数都在运行后，还有任务进来就放入到任务队列中；当任务进来将任务队列都装满了，还有任务进来就启动最大线程数；当最在线程数都在运行后，还有任务进来了，就执行拒绝策略了，就是抛出RejectedExecutionException异常。所以在放入任务时，需要对放入任务的数量进行控制。不然任务任务就被直接RejectedExecutionException给抛出来了。如果直接出异常相关于任务被中断了。所以我会使有并行控制。当然有熟悉线程池的童鞋可能会说使用CallerRunsPolicy这种策略了，这种策略就是如果最大线程都已经被启用，并且队列已经被占用，就是当前提交的线程中执行提交任务了。这其实也不好，如果提交线程还有其他事情需要执行，就被这个任务给占用了。
2. 由于任务提交到了线程池执行了，并不知道任务什么时候执行结束。当所有的排序任务都提交到了线程池了，线程池中排序任务执行排序中，这个时候如果不控制就是开启下一个阶段。当测试不是特别大的文件时。可能就会导致文件读取到了还没有写入完成的文件，导致读取到了错误的数据。

基于这两点，我觉得需要实现一个并行的控制器。

讲完了为什么要做并行控制器。现在聊聊并行控制器如何实现？

我的方案是这样子的：

![](D:\doc\博客\数据结构与算法\大型文件对比\归并排序多文件并行排序-并行控制器.png)

我还是来解释下吧

1. 任务在提交线程池后可到得到一个future对象.

```java
  /**
   * 异常执行
   *
   * @param fileItem 文件
   * @return 异常结果
   */
  private Future<?> runTask(File fileItem) {
    // 以线程池的方式并行运算
    Future<?> future =
        ScheduleTaskThreadPool.INSTANCE.submit(
            () -> {
              long startTime = System.currentTimeMillis();
              // 将文件转换为集合
              List oneFileList = this.fileToList(fileItem);

              // 使用插入排序，保持数据的稳定，不改前数据的前后顺序,但时间复杂度为n的平方.
              Collections.sort(oneFileList);
              // 快速排序，快速排序非稳定的排序，会改变前后顺序。
              // QuickSort.quickSortList(oneFileList);
              // 再输出至文件中
              this.listToFile(oneFileList, fileItem);

              long endTime = System.currentTimeMillis();
              return endTime - startTime;
            });

    return future;
  }
```

2. 将这个任务加入到队列中，当线程池中任务到达满载状态(队列占用完和启动了最大核心数)时，将队列中的首个任务移除，并等待这个任务结束，这样就控制了线程提交的数量和速度了。千万不要忘了，提交结束了，要等任务提交结束。

```java
/**
   * 读取数据的流程处理
   *
   * @param dataList 文件列表
   */
  protected void fileSoft(File[] dataList) {

    System.out.println("开始执行线程池任务，信息输出");
    ScheduleTaskThreadPool.INSTANCE.outPoolInfo();

    LinkedList<Future> rsp = new LinkedList<>();
    try {
      for (File fileItem : dataList) {
        // 异步调用数据处理
        Future<?> future = this.runTask(fileItem);
        // 将数据加入队列尾部
        rsp.add(future);
        // 统计计数
        dataStatistics.dataAdd(1);

        // 优先检查线程池是否已经满载
        if (ScheduleTaskThreadPool.INSTANCE.isFull()) {
          // 获取并移除队列的头
          Future item = rsp.poll();
          // 由于此处单个任务失败，不能影响其他的，需要单个异常处理，流程继续
          try {
            Object value = item.get();
            // 打印任务信息
            System.out.println("用时:" + value);
            ScheduleTaskThreadPool.INSTANCE.outPoolInfo();

          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          }
        }
      }
      // 必须所有都完成
      for (Future item : rsp) {
        Object value = item.get();
        // 打印任务信息
        System.out.println("用时:" + value);
        ScheduleTaskThreadPool.INSTANCE.outPoolInfo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
```

到此并行任务控制器就做好了。



## 线程池

这个应该不用多说了，使用java中的线程池即可。对了，要自己根据资源的情况控制最小线程，最大线程和等待队列和拒绝策略哦。

```java
public class ScheduleTaskThreadPool {

  /** 进行任务调度的线程池对象 */
  public static final ScheduleTaskThreadPool INSTANCE = new ScheduleTaskThreadPool();

  /**
   * 线程池中的核心线程数，当提交一个任务时，线程池创建一个新线程执行任务，直到当前线程数等于corePoolSize；
   * 如果当前线程数为corePoolSize，继续提交的任务被保存到阻塞队列中，等待被执行；
   * 如果执行了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有核心线程。
   */
  private static final int MIN_THREAD_NUM = 2;

  /**
   * 线程池中允许的最大线程数。如果当前阻塞队列满了，且继续提交任务，则创建新的线程执行任务，
   *
   * <p>前提是当前线程数小于maximumPoolSize
   */
  private static final int MAX_THREAD_NUM = 4;

  /**
   * 线程池中允许的最大线程数。如果当前阻塞队列满了，且继续提交任务，则创建新的线程执行任务，
   *
   * <p>前提是当前线程数小于maximumPoolSize
   */
  private static final int WAIT_NUM = 8;

  /**
   * 线程空闲时的存活时间，即当线程没有任务执行时，继续存活的时间。以秒为单位
   *
   * <p>默认情况下，该参数只在线程数大于corePoolSize时才有用
   */
  private static final int KEEPALIVE = 5;

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
          MIN_THREAD_NUM,
          MAX_THREAD_NUM,
          KEEPALIVE,
          TimeUnit.SECONDS,
          queue,
          factory,
          new ThreadPoolExecutor.AbortPolicy());

  /**
   * 提交带返回值的线程给线程池来运行
   *
   * @param task
   */
  public Future<?> submit(Callable task) {
    return pool.submit(task);
  }

  /**
   * 提交任务给线程池运行
   *
   * @param task
   */
  public Future<?> submit(Runnable task) {
    return pool.submit(task);
  }

  /**
   * 当前否为线程池已经满载
   *
   * @return
   */
  public boolean isFull() {
    return pool.getPoolSize() == pool.getMaximumPoolSize();
  }

  public void outPoolInfo() {
    StringBuilder outData = new StringBuilder();
    outData.append("pool Core Size:").append(pool.getCorePoolSize()).append(",");
    outData.append("curr pool size:").append(pool.getPoolSize()).append(",");
    outData.append("max pool Size:").append(pool.getMaximumPoolSize()).append(",");
    outData.append("queue size:").append(pool.getQueue().size()).append(",");
    outData.append("task completed size:").append(pool.getCompletedTaskCount()).append(",");
    outData.append("active count size:").append(pool.getActiveCount()).append(",");
    outData.append("task count size:").append(pool.getTaskCount()).append(",");
    outData.append(Symbol.LINE);
    outData.append(Symbol.LINE);

    System.out.println(outData.toString());
  }

  public void shutdown() {
    pool.shutdown();
  }
}
```



## 总结：

做完了分析是不是觉得也不复杂么，只要将并行控制器和线程池控制好了，这个做起来就没有那么难的，并行控制器，特别注意要与提交速度要与线程池的运行相匹配，不然就是发生RejectedExecutionException异常。线程池这个就相对容易了，使用java自带的线程池就可以了。

至此并行排序就做好了。如果你想查看更多代码，可查看我的github:

https://github.com/kkzfl22/datastruct/blob/master/src/main/java/com/liujun/datastruct/datacompare/bigfilecompare/common/BigFileSort.java



下一节，我将继续分享有序文件的去重和对比输出。