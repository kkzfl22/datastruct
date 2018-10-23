package com.liujun.datastruct.stack;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public class TestLinkedStack {

  @Test
  public void testLinkedStack() {
    LinkedStack linked = new LinkedStack(10);

    linked.push(1);
    linked.push(2);
    linked.push(3);
    linked.push(4);
    linked.push(5);
    linked.push(6);

    System.out.println(linked.pop());
    System.out.println(linked.pop());
    System.out.println(linked.pop());
    System.out.println(linked.pop());
    System.out.println(linked.pop());
    System.out.println(linked.pop());
  }
}
