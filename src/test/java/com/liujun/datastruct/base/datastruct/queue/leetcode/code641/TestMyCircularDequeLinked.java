package com.liujun.datastruct.base.datastruct.queue.leetcode.code641;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/25
 */
public class TestMyCircularDequeLinked {

  @Test
  public void testQueueHead() {
    int size = 10;
    MyCircularDequeLinked queue = new MyCircularDequeLinked(size);

    int index = 0;
    while (index < 100) {

      for (int i = 0; i < size; i++) {
        queue.insertFront(index + i);
        Assert.assertEquals(queue.size(), i + 1);
      }

      Assert.assertEquals(true, queue.isFull());

      for (int i = 0; i < size; i++) {
        int valueDe = queue.getRear();
        queue.deleteLast();
        Assert.assertEquals(valueDe, index + i);
      }

      Assert.assertEquals(true, queue.isEmpty());

      index += 10;
    }
  }

  @Test
  public void testQueueTail() {
    int size = 10;
    MyCircularDequeLinked queue = new MyCircularDequeLinked(size);

    int index = 0;
    while (index < 100) {

      for (int i = 0; i < size; i++) {
        queue.insertLast(index + i);
        Assert.assertEquals(i + 1, queue.size());
      }

      Assert.assertEquals(true, queue.isFull());

      for (int i = 0; i < size; i++) {
        int valueDeFront = queue.getFront();
        queue.deleteFront();
        Assert.assertEquals(valueDeFront, index + i);
      }

      Assert.assertEquals(true, queue.isEmpty());

      index += 10;
    }
  }

  @Test
  public void explame() {
    MyCircularDequeLinked circularDeque = new MyCircularDequeLinked(3); // 设置容量大小为3
    Assert.assertEquals(true, circularDeque.insertLast(1)); // 返回 true
    Assert.assertEquals(true, circularDeque.insertLast(2)); // 返回 true
    Assert.assertEquals(true, circularDeque.insertFront(3)); // 返回 true
    Assert.assertEquals(false, circularDeque.insertFront(4)); // 已经满了，返回 false
    Assert.assertEquals(2, circularDeque.getRear()); // 返回 2
    Assert.assertEquals(true, circularDeque.isFull()); // 返回 true
    Assert.assertEquals(true, circularDeque.deleteLast()); // 返回 true
    Assert.assertEquals(true, circularDeque.insertFront(4)); // 返回 true
    Assert.assertEquals(4, circularDeque.getFront()); // 返回 4
  }
}
