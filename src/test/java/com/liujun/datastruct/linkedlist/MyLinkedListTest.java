package com.liujun.datastruct.linkedlist;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/11
 */
public class MyLinkedListTest {

  @Test
  public void testMyLinkedListAdd() {
    MyLinkedList list = new MyLinkedList();

    list.add(10);
    list.add(20);

    Integer value = list.removeLast();
    System.out.println(value);

    value = list.removeLast();
    System.out.println(value);

    value = list.removeLast();
    System.out.println(value);


    value = list.removeLast();
    System.out.println(value);
  }
}
