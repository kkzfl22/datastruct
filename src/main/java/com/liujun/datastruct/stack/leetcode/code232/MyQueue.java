package com.liujun.datastruct.stack.leetcode.code232;

/**
 * 用栈实现队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class MyQueue {

  /** 标识 */
  public static final int FLAG = -0xFFFFFFF;

  public class Stack {

    /** 存储空间大小 */
    private int[] arrays;

    /** 容量 */
    private int capacity;

    /** 容量 */
    private int size;

    public Stack(int capacity) {
      this.capacity = capacity;
      this.arrays = new int[capacity];
      this.size = 0;
    }

    /**
     * 放入栈
     *
     * @param val
     */
    public void push(int val) {
      if (size >= capacity) {
        return;
      }
      this.arrays[size] = val;
      this.size++;
    }

    /**
     * 栈顶的元素
     *
     * @return 返回栈顶元素
     */
    public int pop() {
      if (size <= 0) {
        return FLAG;
      }
      size--;
      int val = this.arrays[size];

      return val;
    }

    /**
     * 获取栈顶元素
     *
     * @return
     */
    public int peek() {
      if (size <= 0) {
        return FLAG;
      }

      return this.arrays[size - 1];
    }

    public boolean empty() {
      if (size == 0) {
        return true;
      }
      return false;
    }
  }

  private final Stack input;

  private final Stack output;

  /** Initialize your data structure here. */
  public MyQueue() {
    int MAX_SIZE = 128;

    input = new Stack(MAX_SIZE);
    output = new Stack(MAX_SIZE);
  }

  /** Push element x to the back of queue. */
  public void push(int x) {

    // 如果output不为空，需要先先数据移动到input，然后再放入到output中
    while (!output.empty()) {
      int potVal = output.pop();
      input.push(potVal);
    }
    input.push(x);

    while (!input.empty()) {
      int popValue = input.pop();
      output.push(popValue);
    }
  }

  /** Removes the element from in front of queue and returns that element. */
  public int pop() {
    return output.pop();
  }

  /** Get the front element. */
  public int peek() {
    return output.peek();
  }

  /** Returns whether the queue is empty. */
  public boolean empty() {
    return output.empty();
  }
}
