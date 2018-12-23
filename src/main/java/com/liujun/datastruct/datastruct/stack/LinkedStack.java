package com.liujun.datastruct.datastruct.stack;

import com.liujun.datastruct.datastruct.linkedlist.MyLinkedList;

/**
 * 使用数组实现一个顺序栈
 *
 * <p>栈的特性，先进的后出，后进的先出
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public class LinkedStack {

  /** 数据存储的大小 */
  private final MyLinkedList data;

  /** 大小 */
  private int count;

  /** 最大容量 */
  private final int capacity;

  /**
   * 初始化构造函数
   *
   * @param capacity 容量
   */
  public LinkedStack(int capacity) {
    this.data = new MyLinkedList();
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

    this.data.add(value);
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

    int data = this.data.removeLast();

    this.count--;

    return data;
  }
}
