package com.liujun.datastruct.base.datastruct.queue.implementcode;

import org.junit.Assert;

/**
 * 队列的测试信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public class TestQueueBase {

  public static final int QUEUE_DEF_SIZE = 10;

  public void queueput(MyQueueInf arrayQueye) {

    int index = 0;

    while (index < 100) {
      arrayQueye.enqueue(index);

      int deValue = arrayQueye.dequeue();

      Assert.assertEquals(deValue, index);
      Assert.assertEquals(0, arrayQueye.size());

      index++;
    }

    arrayQueye.clean();
  }

  public void queuePutAndTake(MyQueueInf arrayQueye) {
    int index = 0;

    while (index < 100) {
      for (int i = 0; i < QUEUE_DEF_SIZE; i++) {
        arrayQueye.enqueue(index + i);
      }

      Assert.assertEquals(QUEUE_DEF_SIZE, arrayQueye.size());

      index += QUEUE_DEF_SIZE;
      for (int i = 0; i < QUEUE_DEF_SIZE; i++) {
        int deValue = arrayQueye.dequeue();
        Assert.assertEquals(deValue, index - QUEUE_DEF_SIZE + i);
      }
      Assert.assertEquals(0, arrayQueye.size());
    }

    arrayQueye.clean();
  }

  public void queuePutAndTakeMult(MyQueueInf arrayQueye) {

    int index = 0;

    for (int i = 0; i < QUEUE_DEF_SIZE; i++) {
      arrayQueye.enqueue(index + i);
    }
    index += QUEUE_DEF_SIZE;
    for (int i = 0; i < QUEUE_DEF_SIZE / 2; i++) {
      int deValue = arrayQueye.dequeue();
      Assert.assertEquals(deValue, index - QUEUE_DEF_SIZE + i);
    }

    for (int i = 0; i < QUEUE_DEF_SIZE / 2; i++) {
      arrayQueye.enqueue(index + i);
    }

    index = QUEUE_DEF_SIZE;
    for (int i = 0; i < QUEUE_DEF_SIZE; i++) {
      int deValue = arrayQueye.dequeue();
      Assert.assertEquals(deValue, index - QUEUE_DEF_SIZE / 2 + i);
    }

    arrayQueye.clean();
  }
}
