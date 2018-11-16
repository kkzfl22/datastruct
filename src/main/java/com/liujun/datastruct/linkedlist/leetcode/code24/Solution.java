package com.liujun.datastruct.linkedlist.leetcode.code24;

import java.util.List;

/**
 * 两两交换链表中的节点
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class Solution {

  /** 单链表的节点信息 */
  public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
      val = x;
    }
  }

  /** 根节点 */
  public ListNode root = new ListNode(0);

  /** 当前节点 */
  private ListNode curr = root;

  public void putNode(int val) {
    ListNode node = new ListNode(val);
    curr.next = node;
    curr = node;
  }

  public ListNode swapPairs(ListNode input) {

    if (null == input || input.next == null) {
      return input;
    }

    ListNode head = null;
    ListNode tempNode = input;
    ListNode tempNodeNext;
    ListNode lastNode = null;

    while (tempNode != null && tempNode.next != null) {
      tempNodeNext = tempNode.next;
      // 指向头节点
      if (null == head) {
        head = tempNodeNext;
      }
      // 否则指向上一个次的头节点
      else {
        lastNode.next = tempNodeNext;
      }
      // 进行节点的交换操作
      tempNode.next = tempNodeNext.next;
      tempNodeNext.next = tempNode;
      lastNode = tempNode;
      tempNode = tempNode.next;
    }

    return head;
  }

  /**
   * leetcode 上面的标准最好的解
   *
   * @param head 头节点
   * @return 返回重振后的节点
   */
  public ListNode swapPairs2(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode result = dummy;
    while (dummy.next != null && dummy.next.next != null) {
      ListNode temp = dummy.next;
      ListNode next = dummy.next.next.next;
      dummy.next = dummy.next.next;
      dummy.next.next = temp;
      dummy.next.next.next = next;
      dummy = dummy.next.next;
    }
    return result.next;
  }

  public void print(ListNode head) {
    ListNode tempNode = head;

    while (tempNode != null) {
      System.out.print(tempNode.val + "-->");
      tempNode = tempNode.next;
    }

    System.out.println();
  }
}
