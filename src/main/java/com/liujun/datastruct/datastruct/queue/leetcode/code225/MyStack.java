package com.liujun.datastruct.datastruct.queue.leetcode.code225;

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

      findNode.next = node;
    }

    /**
     * 移除首节点
     *
     * @return
     */
    public Integer pop() {
      if (headNode.next != null) {
        Integer value = headNode.next.value;
        headNode.next = headNode.next.next;
        return value;
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

  /** 输入栈 */
  private MyLinkedList input = new MyLinkedList();

  /** 输出栈 */
  private MyLinkedList output = new MyLinkedList();

  /** Initialize your data structure here. */
  public MyStack() {}

  /** Push element x onto stack. */
  public void push(int x) {
    input.push(x);
  }

  /** Removes the element on top of the stack and returns that element. */
  public int pop() {
    Integer last = null;
    // 1,从input队列中将所有的元素加入到输入输出，记录下最后一个
    while (!input.empty()) {
      Integer value = input.pop();
      output.push(value);
      if (null != value) {
        last = value;
      }
    }

    while (!output.empty()) {
      Integer value = output.pop();

      if (!value.equals(last)) {
        input.push(value);
      }
    }

    return last;
  }

  /** Get the top element. */
  public int top() {

    Integer last = null;
    // 1,从input队列中将所有的元素加入到输入输出，记录下最后一个
    while (!input.empty()) {
      Integer value = input.pop();
      output.push(value);
      last = value;
    }

    // 再将输出返回到原队列中
    while (!output.empty()) {
      Integer value = output.pop();
      input.push(value);
    }

    return last;
  }

  /** Returns whether the stack is empty. */
  public boolean empty() {
    return input.empty();
  }
}
