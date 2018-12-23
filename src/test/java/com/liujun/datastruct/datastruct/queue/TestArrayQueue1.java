package com.liujun.datastruct.datastruct.queue;

/**
 * 测试数据队列的实现，只要在有空间，就可以插入数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class TestArrayQueue1 {

  public static void main(String[] args) {
    ArrayQueue1 queue1 = new ArrayQueue1(5);

    queue1.push(1);
    queue1.push(2);
    queue1.push(3);
    queue1.push(4);
    queue1.push(5);

    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());

    queue1.push(1);
    queue1.push(2);
    queue1.push(3);
    queue1.push(4);
    queue1.push(5);

    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());

    System.out.println();

    queue1.push(1);
    queue1.push(2);
    queue1.push(3);
    queue1.push(4);
    queue1.push(5);

    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    queue1.push(4);
    queue1.push(5);
    queue1.push(5);

    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
    System.out.println("获取数据:" + queue1.pop());
  }
}
