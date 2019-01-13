package com.liujun.datastruct.base.datastruct.linkedlist.leetcode.code25;

import com.liujun.datastruct.base.datastruct.linkedlist.leetcode.LinkedListBase;

/**
 * k个一组的翻转链表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class Solution extends LinkedListBase {

  /**
   * 进行k个一组的翻转链表
   *
   * @param head
   * @param k
   */
  public ListNode reverseKGroup(ListNode head, int k) {
    // 如果为空，或者下一级节点为空，不能被翻转
    if (null == head || head.next == null) {
      return head;
    }

    // 返回的链表
    ListNode result = null;

    // 记录下遍历的位置
    ListNode loopFlag = head;
    // 用于翻转链表记录的头
    ListNode resveLinkStart = null;
    // 用于翻转链表记录的尾
    ListNode resveLinkOver = null;
    // 翻转链表需的三个指针，分别头头，当前，以下下一个
    ListNode resHead = null, resCurr, resNext = null;
    // 尾节点
    ListNode resTail = null;

    // 获取K个能够被翻的节点
    int index = 0;
    while (loopFlag != null) {
      if (index <= k - 1) {
        // 如果为空说明为头节点，则需要放入头节点的位置
        if (null == resveLinkStart) {
          resveLinkStart = loopFlag;
          resveLinkOver = resveLinkStart;
        }
        // 否则加入下一级节点的位置
        else {
          resveLinkOver.next = loopFlag;
          resveLinkOver = loopFlag;
        }
      }

      if (index == k - 1) {

        // 将链表进行翻转
        resCurr = resveLinkStart;

        // 记录下尾节点
        if (null == resTail) {
          resTail = resCurr;
        }

        while (resCurr != null) {

          resNext = resCurr.next;

          // 进行翻转操作
          resCurr.next = resHead;
          resHead = resCurr;
          resCurr = resNext;

          if (resHead == resveLinkOver) {
            break;
          }
        }

        // 记录下返回的翻链表
        // 如果为空，将直接加入当前链表
        if (null == result) {
          result = resHead;
        }
        // 否则加入到链表尾部
        else {
          resTail.next = resHead;
          resTail = resveLinkStart;
        }

        resTail.next = resCurr;
        // resTail = resCurr;

        loopFlag = resTail.next;

        resveLinkStart = null;

        index = 0;
      } else {
        index++;
        loopFlag = loopFlag.next;
      }
    }

    if (null != result) {
      return result;
    } else {
      return head;
    }
  }
}
