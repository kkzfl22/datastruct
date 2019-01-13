package com.liujun.datastruct.base.datastruct.linkedlist.leetcode.cod92;

/**
 * 反转链表2的解决方案
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/13
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

  /**
   * 返转一个单链表，在指定的开始与结尾的地方
   *
   * @param headin 输入的单链表
   * @param m 开始位置
   * @param n 结束位置
   * @return 返回返转后的单链表
   */
  public ListNode resverse1(ListNode headin, int m, int n) {

    // 仅一个节点可直接返回
    if (headin.next == null) {
      return headin;
    }

    // 声明在返转区间需要使用的对象
    ListNode head = null, curr = headin, next = null;

    int index = 1;

    // 声明返回的对象链表结构
    ListNode result = null;
    ListNode currNode = result;
    ListNode resveNode = null;

    while (curr != null) {
      // 进行区间返转操作
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
      }
      // 开始的区间依次加入到单链表中
      else {
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
      if (null != resveNode) {
        currNode.next = curr;
      }
    }

    return head;
  }

  /**
   * 在一个单链表中进行区间行的返转
   *
   * <p>此的解答思路是分段进行操作，
   *
   * <p>阶段1 ，扫描区间段前面的，直接加入链表中
   *
   * <p>阶段2，返转操作
   *
   * <p>阶段3，进行追加操作
   *
   * @param headin 链表
   * @param start 开始
   * @param end 结束
   * @return 返回反转后的链表
   */
  public ListNode reserse2(ListNode headin, int start, int end) {

    if (headin.next == null) {
      return headin;
    }

    ListNode result = null;
    ListNode resultLastNode = null;
    ListNode currNode = headin;
    int index = 1;

    if (index < start) {
      result = new ListNode(headin.val);
      resultLastNode = result;
      index++;
      currNode = currNode.next;

      // 1，将头节点相关的加入到链表中
      while (index < start) {
        resultLastNode.next = currNode;
        resultLastNode = currNode;
        index++;
        currNode = currNode.next;
      }
    }

    if (null != currNode) {

      ListNode head = null;
      ListNode curr = currNode;
      ListNode next = null;
      ListNode midLast = null;

      // 2，将中间需要反转的节点加入到链表中
      while (index >= start && index <= end && curr != null) {
        // 指向下一个对象
        next = curr.next;

        if (index == start) {
          midLast = curr;
        }

        curr.next = head;
        head = curr;
        curr = next;

        index++;
      }

      // 将节点加入到单链表中
      if (null != head) {
        if (resultLastNode != null) {
          resultLastNode.next = head;
          // 将链表的尾节点指向中间的尾节点
          resultLastNode = midLast;
        } else {
          result = head;
          resultLastNode = midLast;
        }
      }

      // 将剩余部分的数据加入到链表中
      if (null != curr) {
        resultLastNode.next = curr;
      }
    }
    return result;
  }

  /**
   * leetcoode解法
   *
   * @param head
   * @param m
   * @param n
   * @return
   */
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

    while (temp != null && index < 20) {
      System.out.print(temp.val + "->");
      temp = temp.next;
      index++;
    }

    System.out.println();
  }
}
