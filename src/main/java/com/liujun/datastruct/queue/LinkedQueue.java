package com.liujun.datastruct.queue;

import com.liujun.datastruct.linkedlist.MyLinkedList;

/**
 * 使用链表来实现一个先进先出的队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class LinkedQueue {

  private MyLinkedList linked = new MyLinkedList();

  /**
   * 放入队列的队尾操作
   *
   * @param value 值信息
   */
  public void push(int value) {
    linked.add(value);
  }

  /**
   * 获取队列头节点信息
   *
   * @return
   */
  public int pop() {
    return linked.removeFirst();
  }
}
