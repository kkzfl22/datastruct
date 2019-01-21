package com.liujun.datastruct.base.datastruct.stack;

import org.junit.Test;

/**
 * 测试顺序栈
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public class TestArrayStack {

  @Test
  public void testStackPush() {
    ArrayStack stack = new ArrayStack(8);
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);

    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
  }
}
