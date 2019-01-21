package com.liujun.datastruct.base.datastruct.linkedlist.leetcode;

/**
 * 单链表的基础表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class LinkedListBase {

  /** 单链表的节点信息 */
  public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
      val = x;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("ListNode{");
      sb.append("val=").append(val);
      sb.append('}');
      return sb.toString();
    }
  }

  /** 根节点 */
  public ListNode root = new ListNode(0);

  /** 当前节点 */
  protected ListNode curr = root;

  public void putNode(int val) {
    ListNode node = new ListNode(val);
    curr.next = node;
    curr = node;
  }

  public ListNode finNode(int value) {
    ListNode tempNode = root;

    while (tempNode != null) {
      if (tempNode.val == value) {
        return tempNode;
      }
      tempNode = tempNode.next;
    }

    return null;
  }

  public void putNextNode(ListNode top, ListNode next) {
    top.next = next;
  }

  public void print(ListNode head) {
    ListNode tempNode = head;

    int printIndex = 0;

    while (tempNode != null && printIndex < 50) {
      System.out.print(tempNode.val + "-->");
      tempNode = tempNode.next;
      printIndex++;
      if (printIndex % 10 == 0) {
        System.out.println();
      }
    }

    System.out.println();
  }
}
