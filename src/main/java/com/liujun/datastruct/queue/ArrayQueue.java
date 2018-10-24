package com.liujun.datastruct.queue;

/**
 * 使用数组实现队列
 *
 * <p>仅完成最基本的队列操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class ArrayQueue {

  /** 用来存储数据的数组 */
  private final int[] data;

  /** 当前大小 */
  private int count;

  /** 头指针 */
  private int head;

  /** 总容量大小 */
  private int capacity;

  public ArrayQueue(int capacity) {
    this.data = new int[capacity];
    this.count = 0;
    this.capacity = capacity;
  }

  /**
   * 数据放入操作
   *
   * @param value 参数值
   * @return true 添加成功 false 添加失败
   */
  public boolean push(int value) {
    if (count >= capacity) {
      return false;
    }

    this.data[count] = value;
    count++;

    return true;
  }

  /**
   * 进行出队列操作
   *
   * @return 返回值
   */
  public int pop() {

    if (head >= count) {
      return -1;
    }

    int value = this.data[head];

    head++;

    return value;
  }
}
