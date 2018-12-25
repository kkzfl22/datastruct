package com.liujun.datastruct.datastruct.linkedlist.leetcode.code142;

import com.liujun.datastruct.datastruct.linkedlist.leetcode.LinkedListBase;

/**
 * 返回环形链表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class Solution extends LinkedListBase {

  /**
   * 首先检查环是否存在，
   *
   * <p>1，首先使用快慢指针法来检查是否存在环
   *
   * <p>2 设入环前需要走m步，环中有n个节点。 并设fast和slow相遇时slow总共走过S，
   *
   * <p>则有(S-m)%n = (2S-m)%n （这里是指在环中的位置） 结论 S%n = 0
   *
   * @param head
   * @return
   */
  public ListNode detectCycle(ListNode head) {

    if (head == null) {
      return null;
    }

    // 1,检查环是否存在
    ListNode cycQuickTemp = head;
    ListNode cycLowTemp = head;

    while (cycQuickTemp != null && cycQuickTemp.next != null) {
      cycQuickTemp = cycQuickTemp.next.next;
      cycLowTemp = cycLowTemp.next;
      if (cycLowTemp == cycQuickTemp) {
        cycQuickTemp = head;

        // (S-m)%n = (2S-m)%n （这里是指在环中的位置）
        // 结论 S%n = 0
        while (cycLowTemp != cycQuickTemp) {
          cycLowTemp = cycLowTemp.next;
          cycQuickTemp = cycQuickTemp.next;
        }
        return cycLowTemp;
      }
    }

    return null;
  }

  public ListNode detectCycle2(ListNode head) {
    if (head == null) return null;

    // 步骤一：使用快慢指针判断链表是否有环
    ListNode p = head, p2 = head;
    boolean hasCycle = false;
    while (p2.next != null && p2.next.next != null) {
      p = p.next;
      p2 = p2.next.next;
      if (p == p2) {
        hasCycle = true;
        break;
      }
    }

    // 步骤二：若有环，找到入环开始的节点
    return null;
  }
}
