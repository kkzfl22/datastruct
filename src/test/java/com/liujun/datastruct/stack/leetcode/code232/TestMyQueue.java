package com.liujun.datastruct.stack.leetcode.code232;

import org.junit.Test;

/**
 * 测试用栈实现的队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class TestMyQueue {

  @Test
  public void queue() {
    MyQueue queu = new MyQueue();

    queu.push(1);
    queu.push(2);
    queu.push(3);
    queu.push(4);
    queu.push(5);

    this.loopGet(queu);
    System.out.println("是否为空:" + queu.empty());
  }

  @Test
  public void queue2() {

    int out = -0xFFFFFFF;

    System.out.println(out);

    MyQueue queu = new MyQueue();

    queu.push(-1);
    queu.push(1);
    queu.push(2);
    queu.push(-1);
    queu.push(3);
    queu.push(-1);

    this.loopGet(queu);
    System.out.println("是否为空:" + queu.empty());
  }

  private void loopGet(MyQueue queu) {
    int peek = 0;
    while ((peek = queu.peek()) != MyQueue.FLAG) {

      System.out.println("获取值:" + peek);
      System.out.println("获取移除值:" + queu.pop());
    }
  }
}
