package com.liujun.datastruct.base.datastruct.queue.implementcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * test use array implement queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/23
 */
public class TestMyArrayQueueTwo extends TestQueueBase {

  @Test
  public void linkQueuePut() {

    MyQueueInf queue = new MyArrayQueueTwo(QUEUE_DEF_SIZE);

    this.queueput(queue);
  }

  @Test
  public void linkQueuePutTake() {
    MyQueueInf queue = new MyArrayQueueTwo(QUEUE_DEF_SIZE);

    this.queuePutAndTake(queue);
  }

  @Test
  public void linkQueuePutTakeMult() {
    MyQueueInf queue = new MyArrayQueueTwo(QUEUE_DEF_SIZE);

    this.queuePutAndTakeMult(queue);
  }
}
