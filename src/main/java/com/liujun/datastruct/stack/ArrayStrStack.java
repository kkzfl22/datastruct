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
public class ArrayStrStack {

  /** 数据存储的大小 */
  private final String[] data;

  /** 大小 */
  private int count;

  /** 最大容量 */
  private final int capacity;

  /**
   * 初始化构造函数
   *
   * @param capacity 容量
   */
  public ArrayStrStack(int capacity) {
    this.data = new String[capacity];
    this.count = 0;
    this.capacity = capacity;
  }

  /**
   * 放入数据
   *
   * @param value 当前值
   */
  public boolean push(String value) {
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
  public String pop() {

    if (count == 0) {
      return null;
    }

    String data = this.data[this.count - 1];

    this.count--;

    return data;
  }

  /** 清空栈操作 */
  public void clean() {
    this.count = 0;
  }

  /**
   * 获取栈的大小
   *
   * @return 返回栈的大小操作
   */
  public int size() {
    return count;
  }
}
