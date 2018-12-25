package com.liujun.datastruct.datastruct.queue.leetcode.code225.solution;

import org.junit.Test;

/**
 * 用队列实现栈
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class TestMyStack {

  @Test
  public void stack() {
    MyStack stack = new MyStack();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);

    System.out.println(stack.top());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());

  }
}
