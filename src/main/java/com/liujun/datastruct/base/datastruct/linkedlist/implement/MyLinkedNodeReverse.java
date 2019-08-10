package com.liujun.datastruct.base.datastruct.linkedlist.implement;

/**
 * 单链表的返回
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/30
 */
public class MyLinkedNodeReverse {

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

  /**
   * 进行单链表的返转操作
   *
   * @param node 节点
   * @return 返回反转后的链表
   */
  public static Node reverse(Node node) {
    Node head = node;
    Node curNode = node.next;
    Node nextNode = null;

    while (curNode.next != null) {
      nextNode = curNode.next;

      if (head == node) {
        head.next = null;
      }
      curNode.next = head;
      head = curNode;
      if (nextNode.next != null) {
        curNode = nextNode;
      }
    }

    return curNode;
  }
}
