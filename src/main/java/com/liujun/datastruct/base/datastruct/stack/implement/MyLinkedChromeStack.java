package com.liujun.datastruct.base.datastruct.stack.implement;

/**
 * 使用双向链表实现链浏览器的栈
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/14
 */
public class MyLinkedChromeStack {

  class Node {

    private String val;

    private Node top;

    private Node next;

    public Node(String val) {
      this.val = val;
    }
  }

  private Node root = new Node(null);

  private Node curr = root;

  /** 最大的链式栈大小 */
  private final int maxSize;

  /** 当前大小 */
  private int size;

  public MyLinkedChromeStack(int maxSize) {
    this.maxSize = maxSize;
    this.size = 0;
  }

  /**
   * 插入数据到栈顶
   *
   * @param value
   */
  public void push(String value) {
    if (size > maxSize) {
      throw new IndexOutOfBoundsException("index out of bounds ");
    }
    // 1,获得原来链表的下级节点
    Node valueNode = new Node(value);

    valueNode.top = curr;
    curr.next = valueNode;
    curr = valueNode;

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
  public String pop() {
    // 1,找到栈顶元素
    if (root.next == null) {
      return null;
    }

    String getVal = curr.val;
    // 进行最近数据弹出操作
    Node tmpNodeParent = curr.top;
    tmpNodeParent.next = null;

    curr = tmpNodeParent;

    size--;

    return getVal;
  }

  /**
   * 将最久的元素删除
   *
   * @return
   */
  public String popFirst() {
    if (root.next == null) {
      return null;
    }

    Node tmpDelNode = root.next;
    String value = tmpDelNode.val;

    Node nextNode = tmpDelNode.next;

    // 进行头节点删除
    root.next = nextNode;
    size--;

    return value;
  }
}
