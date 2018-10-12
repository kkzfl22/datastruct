package com.liujun.datastruct.linkedlist;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/11
 */
public class MyLinkedListTest {

  /** 测试添加节点 */
  @Test
  public void testMyLinkedListAdd() {
    MyLinkedList list = new MyLinkedList();

    list.add(10);
    list.add(20);
  }

  /** 测试删除尾节点 */
  @Test
  public void testMylinkedListRemoveLast() {

    MyLinkedList list = new MyLinkedList();

    list.add(10);
    list.add(20);

    Integer value = list.removeLast();
    System.out.println(value);
    System.out.println("2");

    value = list.removeLast();
    System.out.println(value);

    value = list.removeLast();
    System.out.println(value);
  }

  /** 测试查找节点所对应的node */
  @Test
  public void testMyLinkedListFindByValue() {
    MyLinkedList list = new MyLinkedList();
    list.add(10);
    list.add(20);
    list.add(30);
    list.add(40);
    list.add(50);

    MyLinkedList.Node node = list.findByValue(50);

    System.out.println(node);
    Assert.assertNotNull(node);

    MyLinkedList.Node node1 = list.findByValue(90);

    System.out.println(node1);

    Assert.assertNull(node1);
  }

  @Test
  public void testMyLinkedListFindByIndex() {
    MyLinkedList list = new MyLinkedList();
    list.add(10);
    list.add(20);
    list.add(30);

    MyLinkedList.Node index0 = list.findByIndex(0);
    System.out.println(index0);

    index0 = list.findByIndex(1);
    System.out.println(index0);

    index0 = list.findByIndex(2);
    System.out.println(index0);

    index0 = list.findByIndex(3);
    System.out.println(index0);
  }

  @Test
  public void testInsertToHead() {
    MyLinkedList list = new MyLinkedList();
    list.add(10);
    list.add(20);
    list.add(30);

    list.insertToHead(2);

    System.out.println(list);
  }

  @Test
  public void testInsertToAfterValue() {
    MyLinkedList list = new MyLinkedList();
    list.add(10);
    list.add(20);
    list.add(30);

    MyLinkedList.Node node = list.findByIndex(1);

    list.insertToAfter(node, 22);

    System.out.println(list);
  }
}
