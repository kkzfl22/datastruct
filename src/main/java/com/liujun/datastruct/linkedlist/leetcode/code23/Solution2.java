package com.liujun.datastruct.linkedlist.leetcode.code23;

import com.liujun.datastruct.linkedlist.leetcode.LinkedListBase;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/16
 */
public class Solution2 extends LinkedListBase {

  public ListNode mergeKLists(ListNode[] lists) {

    if (lists.length == 0) {
      return null;
    }

    return mergeList(lists, 0, lists.length - 1);
  }

  public ListNode mergeList(ListNode[] lists, int l, int r) {

    if (l == r) {
      return lists[l];
    }

    int mid = l + (r - l) / 2;
    ListNode left = mergeList(lists, l, mid);
    ListNode right = mergeList(lists, mid + 1, r);
    return merge(left, right);
  }

  public ListNode merge(ListNode left, ListNode right) {
    ListNode temp = new ListNode(-1);
    ListNode i = left;
    ListNode j = right;
    ListNode k = temp;
    while (i != null && j != null) {
      if (i.val <= j.val) {
        k.next = i;
        k = k.next;
        i = i.next;
      } else {
        k.next = j;
        k = k.next;
        j = j.next;
      }
    }
    if (i != null) {
      k.next = i;
    }
    if (j != null) {
      k.next = j;
    }
    k = temp.next;

    return k;
  }
}
