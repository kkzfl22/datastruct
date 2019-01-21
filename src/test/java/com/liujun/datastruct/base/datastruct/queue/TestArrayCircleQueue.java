package com.liujun.datastruct.base.datastruct.queue;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class TestArrayCircleQueue {

  public static void main(String[] args) {
    ArrayCircleQueue queue = new ArrayCircleQueue(5);

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);
    queue.push(5);
    System.out.println(queue.push(6));

    System.out.println(queue.pop());
    System.out.println(queue.pop());
    System.out.println(queue.pop());
    System.out.println(queue.pop());
    System.out.println(queue.pop());

    queue.push(8);
    queue.push(9);
    queue.push(10);
  }
}
