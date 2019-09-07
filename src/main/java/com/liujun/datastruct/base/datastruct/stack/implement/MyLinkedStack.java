package com.liujun.datastruct.base.datastruct.stack.implement;

/**
 * 使用链表实现链式栈
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/14
 */
public class MyLinkedStack {

  class Node {

    private int val;

    private Node next;

    public Node(int val) {
      this.val = val;
    }
  }

  private Node root = new Node(-1);

  /** 最大的链式栈大小 */
  private final int maxSize;

  /** 当前大小 */
  private int size;

  public MyLinkedStack(int maxSize) {
    this.maxSize = maxSize;
    this.size = 0;
  }

  /**
   * 插入数据到栈顶
   *
   * @param value
   */
  public void push(int value) {
    if (size > maxSize) {
      throw new IndexOutOfBoundsException("exceed the limit");
    }
    // 1,获得原来链表的下级节点
    Node nextTmp = root.next;

    Node currTmpNode = new Node(value);
    root.next = currTmpNode;
    currTmpNode.next = nextTmp;

    size++;
  }

  public boolean full() {
    return size == maxSize;
  }

  // 获取当前链式栈的大小
  public int size() {
    return size;
  }

  /**
   * 实现弹出栈顶操作
   *
   * @return
   */
  public int pop() {
    if (size < 1) {
      throw new NullPointerException("stack is null");
    }

    // 1,找到栈顶元素
    Node tmpNode = root.next;

    if (tmpNode == null) {
      return -1;
    }

    // 找到下级元素
    Node nextValue = null;
    nextValue = tmpNode.next;

    int value = tmpNode.val;
    root.next = nextValue;

    size--;

    return value;
  }
}
