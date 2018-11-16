package com.liujun.datastruct.linkedlist.leetcode.code141;

import com.liujun.datastruct.linkedlist.leetcode.LinkedListBase;

import java.util.List;

/**
 * 给定一个链表，判断链表中是否有环。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class Solution extends LinkedListBase {

  /**
   * 使用的算法为快慢指针法
   *
   * @param head
   * @return
   */
  public boolean hasCycle(ListNode head) {

    if (head == null) {
      return false;
    }

    ListNode quick = head;
    ListNode slow = head;

    while (quick != null && quick.next != null) {
      slow = slow.next;
      quick = quick.next.next;

      if (quick == slow) {
        return true;
      }
    }

    return false;
  }

  /**
   * 使用循环遍历，来检查，
   *
   * <p>时间复杂度为O(n的平方)
   *
   * @param head
   * @return
   */
  public boolean hasCycle2(ListNode head) {

    if (head == null || head.next == null) {
      return false;
    }

    ListNode node1 = head.next;
    ListNode node2 = node1;

    while (node1 != null) {
      node2 = node1.next;
      while (node2 != null) {
        if (node1 == node2) {
          return true;
        }
        node2 = node2.next;
      }

      node1 = node1.next;
    }

    return false;
  }
}
