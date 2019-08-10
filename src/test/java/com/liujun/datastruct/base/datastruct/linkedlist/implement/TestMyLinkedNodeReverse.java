package com.liujun.datastruct.base.datastruct.linkedlist.implement;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单链表的反转测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/05
 */
public class TestMyLinkedNodeReverse {

  @Test
  public void testNodeResor() {
    MyLinkedNodeReverse.Node node1 = new MyLinkedNodeReverse.Node(1);
    MyLinkedNodeReverse.Node node2 = new MyLinkedNodeReverse.Node(2);
    MyLinkedNodeReverse.Node node3 = new MyLinkedNodeReverse.Node(3);
    MyLinkedNodeReverse.Node node4 = new MyLinkedNodeReverse.Node(4);
    MyLinkedNodeReverse.Node node5 = new MyLinkedNodeReverse.Node(5);
    node1.next = node2;
    node2.next = node3;
    node3.next = node4;
    node4.next = node5;

    MyLinkedNodeReverse.Node newNode = MyLinkedNodeReverse.reverse(node1);
    print(newNode);

    Assert.assertNotEquals(null, newNode);
  }

  private void print(MyLinkedNodeReverse.Node newNode) {
    MyLinkedNodeReverse.Node tmpNode = newNode;

    while (tmpNode.next != null) {
      System.out.print("-->" + tmpNode.value + " ");

      tmpNode = tmpNode.next;
    }
  }
}
