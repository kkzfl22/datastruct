package com.liujun.datastruct.base.datastruct.stack;

import com.liujun.datastruct.base.datastruct.linkedlist.implement.MyLinkedNode;

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
  private final MyLinkedNode data;

  /** 大小 */
  private int count;

  /** 初始化构造函数 */
  public LinkedStack() {
    this.data = new MyLinkedNode();
    this.count = 0;
  }

  /**
   * 放入数据
   *
   * @param value 当前值
   */
  public boolean push(int value) {

    this.data.add(value);
    ++count;

    return true;
  }

  public int get() {
    if (count == 0) {
      return -1;
    }

    return data.getLastValue();
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

    int data = this.data.deleteLast();

    this.count--;

    return data;
  }
}
