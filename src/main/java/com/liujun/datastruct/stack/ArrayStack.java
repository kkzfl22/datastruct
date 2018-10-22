package com.liujun.datastruct.stack;

/**
 * 使用数组实现一个顺序栈
 *
 * <p>栈的特性，先进的后出，后进的先出
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public class ArrayStack {

  /** 数据存储的大小 */
  private final int[] data;

  /** 大小 */
  private int count;

  /** 最大容量 */
  private final int capacity;

  /**
   * 初始化构造函数
   *
   * @param capacity 容量
   */
  public ArrayStack(int capacity) {
    this.data = new int[capacity];
    this.count = 0;
    this.capacity = capacity;
  }

  /**
   * 放入数据
   *
   * @param value 当前值
   */
  public boolean push(int value) {
    if (count > capacity) {
      return false;
    }

    // 1,将数据放入在存储中
    this.data[count] = value;
    ++count;

    return true;
  }

  /**
   * 将数据按先进后出的顺序从数组中取出
   *
   * @return
   */
  public int pop() {

    if (count == 0) {
      return -1;
    }

    int data = this.data[this.count - 1];

    this.count--;

    return data;
  }
}
