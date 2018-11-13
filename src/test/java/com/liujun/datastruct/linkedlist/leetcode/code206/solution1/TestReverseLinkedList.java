package com.liujun.datastruct.linkedlist.leetcode.code206.solution1;

import org.junit.Test;

/**
 * 进行单链表的返转测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/13
 */
public class TestReverseLinkedList {

  @Test
  public void testReverse() {
    ReverseLinkedList reverse = new ReverseLinkedList();

    ReverseLinkedList.ListNode root = this.getLinkedList();
    reverse.printLinkedList(root);
    System.out.println();
    System.out.println("==============================================");

    ReverseLinkedList.ListNode reverseNode = reverse.resverse(root);
    reverse.printLinkedList(reverseNode);
  }

  public void reverse2() {

    ReverseLinkedList reverse = new ReverseLinkedList();

    ReverseLinkedList.ListNode reverseNodeRoot = this.getLinkedList2();
    //
    ReverseLinkedList.ListNode reverseNode = reverse.resverse(reverseNodeRoot, 2, 4);
    reverse.printLinkedList(reverseNode);
  }

  @Test
  public void reverse3() {

    ReverseLinkedList reverse = new ReverseLinkedList();

    ReverseLinkedList.ListNode reverseNodeRoot = this.getLinkedList2();
    //
    ReverseLinkedList.ListNode reverseNode = reverse.reverseBetween(reverseNodeRoot, 2, 4);
    reverse.printLinkedList(reverseNode);
    System.out.println("-----------------------------------");
    reverse.printLinkedList(reverseNodeRoot);
  }

  private ReverseLinkedList.ListNode getLinkedList() {
    ReverseLinkedList reverse = new ReverseLinkedList();

    reverse.put(1);
    reverse.put(2);
    reverse.put(3);
    reverse.put(4);
    reverse.put(5);
    reverse.put(6);
    reverse.put(7);

    return reverse.getRoot();
  }

  private ReverseLinkedList.ListNode getLinkedList2() {
    ReverseLinkedList reverse = new ReverseLinkedList();

    reverse.put(1);
    reverse.put(2);
    reverse.put(3);
    reverse.put(4);
    reverse.put(5);

    return reverse.getRoot();
  }
}
