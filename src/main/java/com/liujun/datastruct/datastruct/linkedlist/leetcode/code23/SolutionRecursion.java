package com.liujun.datastruct.datastruct.linkedlist.leetcode.code23;

import com.liujun.datastruct.datastruct.linkedlist.leetcode.LinkedListBase;

/**
 * 使用递归来解决此合并链表的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/16
 */
public class SolutionRecursion extends LinkedListBase {

  /**
   * 合并多个有序的单链表
   *
   * @param lists
   * @return
   */
  public ListNode mergeKLists(ListNode[] lists) {

    // 1,检查当前是否为只有一个大小
    if (null == lists) {
      return null;
    }

    if (lists.length == 0) {
      return null;
    }

    if (lists.length == 1) {
      return lists[0];
    }
    return recursionMargeList(lists, 0, lists.length - 1);
  }

  /**
   * 使用递归来解决此有序链表合并问题
   *
   * <p>1，递推公式为:margelist(n) = marge(margelist(0,n/2),margelist(n/2+1,n))
   *
   * <p>递归的终止条件为仅存在一个单链表
   *
   * @param lists
   * @param start
   * @param end
   * @return
   */
  public ListNode recursionMargeList(ListNode[] lists, int start, int end) {

    if (start == end) {
      return lists[start];
    }

    int mid = start + (end - start) / 2;
    ListNode left = this.recursionMargeList(lists, start, mid);
    ListNode right = this.recursionMargeList(lists, mid + 1, end);

    return marge(left, right);
  }

  /**
   * 进行链表的合并操作
   *
   * <p>此需要申请额外的空间进行存储
   *
   * @param left
   * @param right
   * @return
   */
  private ListNode marge(ListNode left, ListNode right) {

    ListNode resultNode = new ListNode(-1);
    ListNode resutCurr = resultNode;

    ListNode leftIndex = left;
    ListNode rightIndex = right;

    while (leftIndex != null && rightIndex != null) {
      // 如果左边链表的值小于右边链表的值，则将节点加入到返回链表中
      if (leftIndex.val <= rightIndex.val) {
        resutCurr.next = leftIndex;
        resutCurr = resutCurr.next;
        leftIndex = leftIndex.next;
      }
      // 如果右边的值，比左边小，也同样要加入到返回链表中
      else {
        resutCurr.next = rightIndex;
        resutCurr = resutCurr.next;
        rightIndex = rightIndex.next;
      }
    }

    // 当遍历完成后，检查右边队列是否为空，不为空，则加入到队列中
    if (rightIndex != null) {
      resutCurr.next = rightIndex;
    }
    // 左边队列不为空，则加入到栈中
    else if (leftIndex != null) {
      resutCurr.next = leftIndex;
    }

    return resultNode.next;
  }
}
