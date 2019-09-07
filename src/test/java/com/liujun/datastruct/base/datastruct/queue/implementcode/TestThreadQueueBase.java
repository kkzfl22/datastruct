package com.liujun.datastruct.base.datastruct.queue.implementcode;

import java.util.concurrent.CountDownLatch;

/**
 * 队列的测试信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public class TestThreadQueueBase extends TestQueueBase {

  private static final int MAX_VALUE = 100000;

  public void threadQueueOneAndOne(MyQueueInf queue) throws InterruptedException {

    int max = MAX_VALUE;

    int numthread = 1;

    CountDownLatch produceLatch = new CountDownLatch(numthread);

    CountDownLatch latch = new CountDownLatch(max);

    // 获取生产者线程
    Runnable produceThread = this.getProduceLoop(queue, produceLatch);

    // 消费者线程
    Runnable comsumerThread = this.getConsumer(queue, latch);

    new Thread(produceThread).start();
    new Thread(comsumerThread).start();

    produceLatch.await();
    latch.await();
  }

  /**
   * 获取生产者对象
   *
   * @param queue 队列
   * @param produceLatch 控制开始的闭锁
   * @return
   */
  private Runnable getProduceLoop(MyQueueInf queue, CountDownLatch produceLatch) {
    return () -> {
      produceLatch.countDown();
      long index = ((int) Thread.currentThread().getId() + 1) * 100;

      while (true) {
        queue.enqueue((int) index++);
      }
    };
  }

  private Runnable getConsumer(MyQueueInf queue, CountDownLatch conSumerLatch) {
    return () -> {
      int value;

      while (true) {
        value = queue.dequeue();
        conSumerLatch.countDown();

        if (value % 10000 == 0) {
          System.out.println(
              "curr index:"
                  + Thread.currentThread().getId()
                  + ":value:"
                  + value
                  + ",latch:"
                  + conSumerLatch.getCount());
        }
      }
    };
  }

  public void threadQueueMultAndMult(MyQueueInf queue) throws InterruptedException {

    int numthread = 10;

    // 生产者线程
    CountDownLatch produceLatch = new CountDownLatch(numthread);
    // 消费者线程
    CountDownLatch conSumerLatch = new CountDownLatch(MAX_VALUE);

    Runnable produceThread = this.getProduceLoop(queue, produceLatch);

    Runnable conSumerThread = this.getConsumer(queue, conSumerLatch);

    for (int i = 0; i < numthread; i++) {
      new Thread(produceThread).start();
    }

    produceLatch.await();

    for (int i = 0; i < numthread; i++) {
      new Thread(conSumerThread).start();
    }

    conSumerLatch.await();
  }

  public void threadQueueMultAndOne(MyQueueInf queue) throws InterruptedException {

    int max = MAX_VALUE;

    int numthread = 10;

    CountDownLatch produceLatch = new CountDownLatch(numthread);
    CountDownLatch conSumerLatch = new CountDownLatch(max);

    // 生产者线程
    Runnable produceThread = this.getProduceLoop(queue, produceLatch);
    // 消费者线程
    Runnable conSumerThread = this.getConsumer(queue, conSumerLatch);

    for (int i = 0; i < numthread; i++) {
      new Thread(produceThread).start();
    }

    produceLatch.await();

    new Thread(conSumerThread).start();

    conSumerLatch.await();
  }

  public void threadQueueOneAndMult(MyQueueInf queue) throws InterruptedException {

    int max = MAX_VALUE;

    int numthread = 10;

    CountDownLatch produceLatch = new CountDownLatch(1);
    CountDownLatch conSumerLatch = new CountDownLatch(max);

    // 生产者线程
    Runnable produceThread = this.getProduceLoop(queue, produceLatch);
    // 消费者线程
    Runnable conSumerThread = this.getConsumer(queue, conSumerLatch);

    new Thread(produceThread).start();

    produceLatch.await();

    for (int i = 0; i < numthread; i++) {
      new Thread(conSumerThread).start();
    }
    conSumerLatch.await();
  }
}
