package com.liujun.datastruct.base.datastruct.queue.implementcode;

import org.junit.Test;

/**
 * test use linked implement queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/23
 */
public class TestMyLinkedQueue extends TestQueueBase {

  @Test
  public void linkQueuePut() {

    MyQueueInf queue = new MyLinkedQueue(QUEUE_DEF_SIZE);

    this.queueput(queue);
  }

  @Test
  public void linkQueuePutTake() {
    MyQueueInf queue = new MyLinkedQueue(QUEUE_DEF_SIZE);

    this.queuePutAndTake(queue);
  }

  @Test
  public void linkQueuePutTakeMult() {
    MyQueueInf queue = new MyLinkedQueue(QUEUE_DEF_SIZE);

    this.queuePutAndTakeMult(queue);
  }
}
