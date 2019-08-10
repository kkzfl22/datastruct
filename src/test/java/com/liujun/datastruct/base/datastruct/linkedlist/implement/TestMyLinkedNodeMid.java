package com.liujun.datastruct.base.datastruct.linkedlist.implement;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试求链表的中音节点
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/06
 */
public class TestMyLinkedNodeMid {

  @Test
  public void testMid() {
    MyLinkedNodeMid.Node node = getNode(1, 2, 3, 4, 5);
    MyLinkedNodeMid instance = new MyLinkedNodeMid();
    MyLinkedNodeMid.Node mid = instance.mid(node);
    Assert.assertEquals(3, mid.value);
  }

  @Test
  public void testMid2() {
    MyLinkedNodeMid.Node node = getNode(1, 2, 3, 4, 5, 6);
    MyLinkedNodeMid instance = new MyLinkedNodeMid();
    MyLinkedNodeMid.Node mid = instance.mid(node);
    Assert.assertEquals(4, mid.value);
  }

  public MyLinkedNodeMid.Node getNode(int... value) {

    MyLinkedNodeMid.Node noderoot = new MyLinkedNodeMid.Node(-1);
    MyLinkedNodeMid.Node tempNode = noderoot;

    for (int valuItem : value) {
      MyLinkedNodeMid.Node nodeItem = new MyLinkedNodeMid.Node(valuItem);
      tempNode.next = nodeItem;
      tempNode = nodeItem;
    }

    return noderoot.next;
  }
}
