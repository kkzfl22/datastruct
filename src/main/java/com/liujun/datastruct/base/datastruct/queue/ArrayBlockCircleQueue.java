package com.liujun.datastruct.base.datastruct.queue;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发循环队列，使用数组来实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class ArrayBlockCircleQueue {

  /** 数据类型 */
  private int[] data;

  /** 当前写入的位置 */
  private final AtomicInteger tail;

  /** 头位置 */
  private final AtomicInteger head;

  /** 容量 */
  private int capacity;

  public ArrayBlockCircleQueue(int capacity) {
    this.data = new int[capacity];
    this.tail = new AtomicInteger(0);
    this.head = new AtomicInteger(0);
    this.capacity = capacity;
  }

  public void print() {
    System.out.print(Arrays.toString(data));
  }

  /**
   * 放入数据
   *
   * @param value 值信息
   */
  public boolean push(int value) {
    // 检查是否队列已经满,即当下一个存储的值为道节点，即为队列已经满了
    if ((tail.get() + 1) % capacity == head.get()) {
      return false;
    }

    int tailValue = tail.get();
    int updValue = (tailValue + 1) % capacity;

    if (tail.compareAndSet(tailValue, updValue)) {
      this.data[tailValue] = value;
      return true;
    }

    return false;
  }

  /**
   * 从循环队列中获取数据
   *
   * @return 结果值
   */
  public int pop() {
    // 首先检查队列是否为空
    if (tail.get() == head.get()) {
      return -1;
    }

    int popBefore = head.get();
    int popAfter = (popBefore + 1) % capacity;

    if (head.compareAndSet(popBefore, popAfter)) {
      int value = this.data[popBefore];
      return value;
    }

    return -1;
  }
}