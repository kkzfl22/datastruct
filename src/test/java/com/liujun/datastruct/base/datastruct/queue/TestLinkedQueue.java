package com.liujun.datastruct.base.datastruct.queue;

/**
 * 测试使用队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class TestLinkedQueue {

  public static void main(String[] args) {
    LinkedQueue queue = new LinkedQueue();

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);
    queue.push(5);

    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());

    queue.push(8);
    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());

    System.out.println();

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);
    queue.push(5);

    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());
    System.out.println("获取值:" + queue.pop());
  }
}
