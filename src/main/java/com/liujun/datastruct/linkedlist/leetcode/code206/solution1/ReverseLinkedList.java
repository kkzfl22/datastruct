package com.liujun.datastruct.linkedlist.leetcode.code206.solution1;

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

  public ListNode resverse(ListNode headin, int m, int n) {

    if (headin.next == null) {
      return headin;
    }

    ListNode head = null, curr = headin, next = null;

    int index = 1;

    ListNode result = null;
    ListNode currNode = result;
    ListNode resveNode = null;

    while (curr != null) {
      if (index >= m && index <= n) {
        if (index == m) {
          resveNode = curr;
        }

        next = curr.next;
        curr.next = head;
        head = curr;
        curr = next;

      } else if (index > n) {
        break;
      } else {
        if (currNode == null) {
          result = new ListNode(curr.val);
          currNode = result;
        }

        if (index + 1 < m) {
          currNode.next = curr.next;
          currNode = currNode.next;
        }

        curr = curr.next;
      }

      index++;
    }

    if (currNode != null) {
      // 将后续节点加入到链表中
      currNode.next = head;
      currNode = resveNode;
      if (null != currNode) {
        currNode.next = curr;
      }
      return result;
    }

    if (null != curr) {
      currNode = resveNode;
      currNode.next = curr;
    }

    return head;
  }

  public ListNode reverseBetween(ListNode head, int m, int n) {
    if (head == null || head.next == null) return head;
    ListNode virtual = new ListNode(0);
    virtual.next = head;

    int k = m;
    ListNode rear = virtual, front = rear.next;
    while (k > 1) {
      rear = rear.next;
      front = front.next;
      k--;
    }

    k = n - m;
    if (k == 0) return virtual.next;
    ListNode p = front.next, q = front;
    if (p.next == null) {
      rear.next = p;
      p.next = front;
      front.next = null;
      return virtual.next;
    }
    while (k > 0) {
      front.next = p.next;
      rear.next = p;
      p.next = q;
      q = p;
      p = front.next;
      k--;
    }
    return virtual.next;
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
