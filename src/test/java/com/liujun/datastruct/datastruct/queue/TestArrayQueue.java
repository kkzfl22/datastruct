package com.liujun.datastruct.datastruct.queue;

/**
 * 测试最简单的队列实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class TestArrayQueue {

  public static void main(String[] args) {
    ArrayQueue queue = new ArrayQueue(5);

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);
    queue.push(5);
    queue.push(5);

    System.out.println("获取:" + queue.pop());
    System.out.println("获取:" + queue.pop());
    System.out.println("获取:" + queue.pop());
    System.out.println("获取:" + queue.pop());
    System.out.println("获取:" + queue.pop());

  }
}
