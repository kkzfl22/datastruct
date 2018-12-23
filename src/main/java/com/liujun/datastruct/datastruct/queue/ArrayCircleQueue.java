package com.liujun.datastruct.datastruct.queue;

/**
 * 循环队列，使用数组来实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class ArrayCircleQueue {

  /** 数据类型 */
  private int[] data;

  /** 当前写入的位置 */
  private int tail;

  /** 头位置 */
  private int head;

  /** 容量 */
  private int capacity;

  public ArrayCircleQueue(int capacity) {
    this.data = new int[capacity];
    this.tail = 0;
    this.head = 0;
    this.capacity = capacity;
  }

  /**
   * 放入数据
   *
   * @param value 值信息
   */
  public boolean push(int value) {
    // 检查是否队列已经满,即当下一个存储的值为道节点，即为队列已经满了
    if ((tail + 1) % capacity == head) {
      return false;
    }

    this.data[tail] = value;
    tail = (tail + 1) % capacity;

    return true;
  }

  /**
   * 从循环队列中获取数据
   *
   * @return 结果值
   */
  public int pop() {
    // 首先检查队列是否为空
    if (tail == head) {
      return -1;
    }

    int value = this.data[head];
    head = (head + 1) % capacity;

    return value;
  }
}
