package com.liujun.datastruct.base.datastruct.queue.implementcode;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/25
 */
public class TestMyCycleQueue extends TestQueueBase {

  @Test
  public void linkQueuePut() {

    MyQueueInf queue = new MyCycleQueue(QUEUE_DEF_SIZE + 1);

    this.queueput(queue);
  }

  @Test
  public void linkQueuePutTake() {
    MyQueueInf queue = new MyCycleQueue(QUEUE_DEF_SIZE + 1);

    this.queuePutAndTake(queue);
  }

  @Test
  public void linkQueuePutTakeMult() {
    MyQueueInf queue = new MyCycleQueue(QUEUE_DEF_SIZE + 1);

    this.queuePutAndTakeMult(queue);
  }
}
