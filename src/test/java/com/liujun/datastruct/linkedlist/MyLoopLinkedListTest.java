package com.liujun.datastruct.linkedlist;

import org.junit.Test;

/**
 * 进行循环链表的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/13
 */
public class MyLoopLinkedListTest {

  @Test
  public void add() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    loopLinked.printTreeNode(null, 1);
  }

  @Test
  public void removeLast() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    System.out.println(loopLinked.size());

    loopLinked.printTreeNode(null, 1);

    System.out.println();

    loopLinked.removeLast();
    loopLinked.removeLast();
    loopLinked.removeLast();
    loopLinked.removeLast();
    loopLinked.removeLast();
    loopLinked.removeLast();
    loopLinked.removeLast();
    loopLinked.removeLast();

    loopLinked.add(20);
    loopLinked.add(30);

    loopLinked.printTreeNode(null, 1);
  }

  @Test
  public void findbyIndex() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    MyLoopLinkedList.Node node = loopLinked.findIndex(3);
    System.out.println(node);

    node = loopLinked.findIndex(2);
    System.out.println(node);

    node = loopLinked.findIndex(0);
    System.out.println(node);
  }

  @Test
  public void inserToHead() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    loopLinked.insertToHead(2);

    loopLinked.printTreeNode(null, 1);
  }

  @Test
  public void findByValue() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    MyLoopLinkedList.Node node = loopLinked.findByValue(30);

    System.out.println(node);
  }

  @Test
  public void insertToBefore() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    MyLoopLinkedList.Node findNode = loopLinked.findIndex(2);

    System.out.println(findNode);

    loopLinked.insertToBefore(findNode, 22);

    loopLinked.printTreeNode(null, 1);
  }

  @Test
  public void insertToAfter() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    MyLoopLinkedList.Node findNode = loopLinked.findIndex(2);

    loopLinked.insertToAfter(findNode, 32);

    loopLinked.printTreeNode(null, 1);
  }

  @Test
  public void deleteByNode() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    MyLoopLinkedList.Node valueNode = loopLinked.findByValue(30);

    loopLinked.deleteByNode(valueNode);

    loopLinked.printTreeNode(null, 1);
  }

  @Test
  public void deleteByValueNode() {
    MyLoopLinkedList loopLinked = new MyLoopLinkedList();

    loopLinked.add(10);
    loopLinked.add(20);
    loopLinked.add(30);
    loopLinked.add(40);
    loopLinked.add(50);

    loopLinked.deleteByValue(30);

    loopLinked.printTreeNode(null, 1);
  }
}
