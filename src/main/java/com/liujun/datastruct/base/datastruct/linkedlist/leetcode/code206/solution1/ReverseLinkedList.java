package com.liujun.datastruct.base.datastruct.linkedlist.leetcode.code206.solution1;

/**
 * 返转单链表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/13
 */
public class ReverseLinkedList {

  /** 单链表的节点信息 */
  public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
      val = x;
    }
  }

  /** 根节点 */
  private ListNode root;

  /** 当前节点 */
  private ListNode curr;

  public ListNode getRoot() {
    return root;
  }

  public void put(int value) {

    if (null == root) {
      root = new ListNode(value);
      curr = root;
    } else {
      ListNode add = new ListNode(value);
      curr.next = add;
      curr = add;
    }
  }

  public ListNode resverse(ListNode headin) {
    ListNode head = null, curr = headin, next = null;

    while (curr != null) {
      next = curr.next;
      curr.next = head;
      head = curr;
      curr = next;
    }

    return head;
  }

  public int getSize(ListNode headin) {
    ListNode temp = headin;

    int size = 0;

    int index = 0;

    while (temp != null && index < 50) {
      temp = temp.next;
      index++;
      size++;
    }

    return size;
  }



  public void printLinkedList(ListNode headin) {
    ListNode temp = headin;

    int index = 0;

    while (temp != null && index < 50) {
      System.out.print(temp.val + "->");
      temp = temp.next;
      index++;
    }
  }
}
