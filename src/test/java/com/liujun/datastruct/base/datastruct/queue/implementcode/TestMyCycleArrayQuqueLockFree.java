package com.liujun.datastruct.base.datastruct.queue.implementcode;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public class TestMyCycleArrayQuqueLockFree extends TestThreadQueueBase {

  @Test
  public void linkQueuePut() {

    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.queueput(queue);
  }

  @Test
  public void linkQueuePutTake() {
    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.queuePutAndTake(queue);
  }

  @Test
  public void linkQueuePutTakeMult() {
    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.queuePutAndTakeMult(queue);
  }

  @Test
  public void threadQueueOneAndOne() throws InterruptedException {
    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.threadQueueOneAndOne(queue);
  }

  @Test
  public void threadQueueOneAndMult() throws InterruptedException {
    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.threadQueueOneAndMult(queue);
  }

  @Test
  public void threadQueueMultAndMult() throws InterruptedException {
    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.threadQueueMultAndMult(queue);
  }

  @Test
  public void threadQueueMultAndone() throws InterruptedException {
    MyQueueInf queue = new MyCycleArrayQuqueLockFree(QUEUE_DEF_SIZE + 1);

    this.threadQueueMultAndOne(queue);
  }
}
