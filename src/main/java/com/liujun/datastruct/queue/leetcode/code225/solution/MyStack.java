package com.liujun.datastruct.queue.leetcode.code225.solution;

/**
 * 使用队列来实现栈的效果
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class MyStack {

  /** 队列信息 */
  class MyLinkedList {

    /** 链表的节点信息 */
    class Node {
      private Integer value;
      private Node next;

      public Node(Integer value) {
        this.value = value;
      }

      public Integer getValue() {
        return value;
      }
    }

    /** 头链表信息 */
    private Node headNode = new Node(null);

    /** 大小 */
    private int size;

    /**
     * 加入到尾节点中节点信息
     *
     * @param value
     */
    public void push(int value) {
      Node node = new Node(value);
      Node findNode = headNode;

      while (findNode.next != null) {
        findNode = findNode.next;
      }

      size++;
      findNode.next = node;
    }

    /**
     * 移除首节点
     *
     * @return
     */
    public Integer pop() {
      if (headNode.next != null) {
        size--;
        Integer value = headNode.next.value;
        headNode.next = headNode.next.next;
        return value;
      }
      return null;
    }

    public int size() {
      return size;
    }

    /**
     * 移除首节点
     *
     * @return
     */
    public Integer peek() {
      if (headNode.next != null) {
        return headNode.next.value;
      }
      return null;
    }

    /**
     * 检查是否为空
     *
     * @return
     */
    public boolean empty() {
      if (headNode.next == null) {
        return true;
      }
      return false;
    }
  }

  /** 输出栈 */
  private MyLinkedList output = new MyLinkedList();

  /** Initialize your data structure here. */
  public MyStack() {}

  /** Push element x onto stack. */
  public void push(int x) {
    MyLinkedList input = new MyLinkedList();
    int outSize = output.size;

    // 首先将原来的中数据放入到队列中
    for (int i = 0; i < outSize; i++) {
      input.push(output.pop());
    }

    // 将当我前的数据加入到最前面
    output.push(x);

    // 然后放回到原队列中
    for (int i = 0; i < outSize; i++) {
      output.push(input.pop());
    }
  }

  /** Removes the element on top of the stack and returns that element. */
  public int pop() {
    return (int) output.pop();
  }

  /** Get the top element. */
  public int top() {
    return (int) output.peek();
  }

  /** Returns whether the stack is empty. */
  public boolean empty() {
    return output.empty();
  }
}
