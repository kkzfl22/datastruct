package com.liujun.datastruct.datastruct.linkedlist.leetcode.code141;

import com.liujun.datastruct.datastruct.linkedlist.leetcode.LinkedListBase;

import java.util.HashSet;
import java.util.Set;

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

    if (head == null && head.next != null) {
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
   * 使用另外的一个空间来存储链表节点，然后检查是否在链表中
   *
   * <p>空间复杂度为O(n)
   *
   * @param head
   * @return
   */
  public boolean hasCycle2(ListNode head) {

    if (head == null) {
      return false;
    }

    Set<ListNode> nodeSet = new HashSet<>();

    ListNode currNode = head;

    while (currNode != null) {
      if (nodeSet.contains(currNode)) {
        return true;
      } else {
        nodeSet.add(currNode);
      }

      currNode = currNode.next;
    }

    return false;
  }
}
