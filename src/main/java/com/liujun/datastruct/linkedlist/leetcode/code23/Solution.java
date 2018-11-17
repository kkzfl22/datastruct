package com.liujun.datastruct.linkedlist.leetcode.code23;

import com.liujun.datastruct.linkedlist.leetcode.LinkedListBase;

/**
 * 合并多个有序的单链表
 *
 * <p>时间复杂度为O(n*m)
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/16
 */
public class Solution extends LinkedListBase {

  /**
   * 合并多个有序的单链表
   *
   * @param lists
   * @return
   */
  public ListNode mergeKLists(ListNode[] lists) {

    // 如果为空
    if (lists == null || lists.length == 0) {
      return null;
    }
    // 如果长度为1
    if (lists.length == 1) {
      return lists[0];
    }

    ListNode resultNod = new ListNode(0);
    ListNode currNode = resultNod;

    // 记录下第个文件遍历的位置
    ListNode[] marketFlag = lists;

    ListNode findMinNode;

    // 遍历查找最小，返回
    while ((findMinNode = findMin(marketFlag)) != null) {
      currNode.next = findMinNode;
      currNode = findMinNode;
    }
    return resultNod.next;
  }

  /**
   * 在集合中查找最小的
   *
   * @param marketFlag
   * @return
   */
  private ListNode findMin(ListNode[] marketFlag) {

    int markIndex = 0;
    ListNode node;

    ListNode findMin = null;

    // 查找最小的对象
    for (int i = 0; i < marketFlag.length; i++) {
      node = marketFlag[i];

      if (null == findMin && node != null) {
        findMin = node;
        markIndex = i;
      }

      if (null != node && node.val < findMin.val) {
        findMin = node;
        markIndex = i;
      }
    }

    if (null != marketFlag[markIndex]) {
      // 找到后，将指向下一个
      marketFlag[markIndex] = marketFlag[markIndex].next;
    }

    return findMin;
  }
}
