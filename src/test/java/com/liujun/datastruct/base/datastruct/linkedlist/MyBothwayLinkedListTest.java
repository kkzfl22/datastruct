package com.liujun.datastruct.base.datastruct.linkedlist;

import org.junit.Test;

/**
 * 双向循环链表的代码测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/13
 */
public class MyBothwayLinkedListTest {

  @Test
  public void add() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);

    bothwayLinked.printTreeNode(null, 1);
  }

  @Test
  public void removeLast() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    bothwayLinked.printTreeNode(null, 1);

    System.out.println();
    System.out.println();

    bothwayLinked.removeLast();
    bothwayLinked.removeLast();
    bothwayLinked.removeLast();

    bothwayLinked.printTreeNode(null, 1);
  }

  @Test
  public void findByIndex() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    MyBothwayLinkedList.Node node = bothwayLinked.findByIndex(2);
    System.out.println(node);
  }

  @Test
  public void findByValue() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    MyBothwayLinkedList.Node node = bothwayLinked.findByValue(20);
    System.out.println(node);
  }

  @Test
  public void insertToHead() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    bothwayLinked.insertToHead(8);
    bothwayLinked.printTreeNode(null, 1);

    System.out.println();

    bothwayLinked.insertToHead(4);
    bothwayLinked.insertToHead(1);
    bothwayLinked.printTreeNode(null, 1);
  }

  @Test
  public void insertToAfter() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    bothwayLinked.printTreeNode(null, 1);

    MyBothwayLinkedList.Node findNode = bothwayLinked.findByIndex(1);

    System.out.println();
    System.out.println();
    bothwayLinked.insertToAfter(findNode, 23);
    bothwayLinked.printTreeNode(null, 1);
  }

  @Test
  public void insertToBefore() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    MyBothwayLinkedList.Node findNode = bothwayLinked.findByValue(30);
    bothwayLinked.insertToBefore(findNode, 23);

    bothwayLinked.printTreeNode(null, 1);

    System.out.println();
    System.out.println();

    MyBothwayLinkedList.Node findNode2 = bothwayLinked.findByValue(40);
    bothwayLinked.insertToBefore(findNode2, 35);

    bothwayLinked.printTreeNode(null, 1);
  }

  @Test
  public void deleteByNode() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);

    MyBothwayLinkedList.Node findNode = bothwayLinked.findByValue(30);

    bothwayLinked.deleteByNode(findNode);

    bothwayLinked.printTreeNode(null, 1);
  }

  @Test
  public void deleteByValue() {
    MyBothwayLinkedList bothwayLinked = new MyBothwayLinkedList();

    bothwayLinked.add(10);
    bothwayLinked.add(20);
    bothwayLinked.add(30);
    bothwayLinked.add(40);
    bothwayLinked.add(50);

    bothwayLinked.deleteByValue(40);

    bothwayLinked.printTreeNode(null, 1);
  }
}
