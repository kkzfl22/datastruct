package com.liujun.datastruct.base.datastruct.queue.leetcode.code622;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试循环队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/29
 */
public class TestMyCircularQueue {

  @Test
  public void testqueu() {
    MyCircularQueue circularQueue = new MyCircularQueue(3); // set the size to be 3
    Assert.assertEquals(true, circularQueue.enQueue(1)); // return true
    Assert.assertEquals(true, circularQueue.enQueue(2)); // return true
    Assert.assertEquals(true, circularQueue.enQueue(3)); // return true
    Assert.assertEquals(false, circularQueue.enQueue(4)); // return false, the queue is full
    Assert.assertEquals(3, circularQueue.Rear()); // return 3
    Assert.assertEquals(true, circularQueue.isFull()); // return true
    Assert.assertEquals(true, circularQueue.deQueue()); // return true
    Assert.assertEquals(true, circularQueue.enQueue(4)); // return true
    Assert.assertEquals(4, circularQueue.Rear()); // return 4
  }

  @Test
  public void testQueue() {

    int maxQueue = 1;

    MyCircularQueue arrayQueye = new MyCircularQueue(maxQueue);

    int index = 0;

    while (index < 100) {
      arrayQueye.enQueue(index);

      boolean deValue = arrayQueye.deQueue();

      Assert.assertEquals(deValue, true);
      Assert.assertEquals(0, arrayQueye.size());

      index++;
    }
  }

  @Test
  public void testMult() {
    int index = 0;
    int QUEUE_DEF_SIZE = 10;
    MyCircularQueue arrayQueye = new MyCircularQueue(QUEUE_DEF_SIZE);

    while (index < 100) {
      for (int i = 0; i < QUEUE_DEF_SIZE; i++) {
        arrayQueye.enQueue(index + i);
      }

      Assert.assertEquals(QUEUE_DEF_SIZE, arrayQueye.size());

      index += QUEUE_DEF_SIZE;
      for (int i = 0; i < QUEUE_DEF_SIZE; i++) {
        boolean deValue = arrayQueye.deQueue();
        Assert.assertEquals(deValue, true);
      }
      Assert.assertEquals(0, arrayQueye.size());
    }
  }

  public void test() {
    //
    // ["MyCircularQueue","enQueue","Rear","Rear","deQueue","enQueue","Rear","deQueue","Front","deQueue","deQueue","deQueue"]
    // [[6],[6],[],[],[],[5],[],[],[],[],[],[]]
  }
}
