package com.liujun.datastruct.base.datastruct.queue.implementcode;

import org.junit.Test;

/**
 * test blcoker linked queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/29
 */
public class TestMyBlockerLinkedQuque extends TestThreadQueueBase {

  @Test
  public void linkQueuePut() {

    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.queueput(queue);
  }

  @Test
  public void linkQueuePutTake() {
    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.queuePutAndTake(queue);
  }

  @Test
  public void linkQueuePutTakeMult() {
    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.queuePutAndTakeMult(queue);
  }

  @Test
  public void threadQueueOneAndOne() throws InterruptedException {
    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.threadQueueOneAndOne(queue);
  }

  @Test
  public void threadQueueOneAndMult() throws InterruptedException {
    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.threadQueueOneAndMult(queue);
  }

  @Test
  public void threadQueueMultAndMult() throws InterruptedException {
    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.threadQueueMultAndMult(queue);
  }

  @Test
  public void threadQueueMultAndone() throws InterruptedException {
    MyQueueInf queue = new MyBlockerLinkedQuque(QUEUE_DEF_SIZE);

    this.threadQueueMultAndOne(queue);
  }
}
