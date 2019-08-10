package com.liujun.datastruct.base.datastruct.linkedlist.implement;

/**
 * 求链表的中间节点
 *
 * <p>利用的原理是快慢指针的方法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/30
 */
public class MyLinkedNodeMid {

  /** 单链表的节点信息 */
  static class Node {
    /** 当前值 */
    int value;

    /** 下一个节点 */
    Node next;

    public Node(int value) {
      this.value = value;
    }
  }

  public Node mid(Node node) {
    // 通过快慢指名求中间节点
    if (node == null || node.next == null) {
      return null;
    }

    Node slow = node.next;
    Node quick = node.next.next;

    // 利用快慢指针可以求出
    while (quick != null && quick.next != null) {
      slow = slow.next;
      quick = quick.next.next;
    }

    return slow;
  }
}
