package com.liujun.datastruct.linkedlist.leetcode.cod92;

import org.junit.Test;

/**
 * 进行单链表的返转测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/13
 */
public class TestSolution {

  @Test
  public void testReverse() {
    Solution reverse = new Solution();

    Solution.ListNode root = this.getLinkedList();
    Solution.ListNode reverseNode = reverse.reserse2(root, 2, 4);
    reverse.printLinkedList(reverseNode);
  }

  @Test
  public void testReverse2() {
    Solution reverse = new Solution();

    for (int i = 1; i <= 5; i++) {
      Solution.ListNode root = this.getLinkedList();
      Solution.ListNode reverseNode = reverse.reserse2(root, i, 5);
      // System.out.println("结果打印");
      reverse.printLinkedList(reverseNode);
      System.out.println();
      System.out.println("--------------------------------------------");
      System.out.println();
    }
  }

  private Solution.ListNode getLinkedList() {
    Solution reverse = new Solution();

    reverse.put(1);
    reverse.put(2);
    reverse.put(3);
    reverse.put(4);
    reverse.put(5);

    return reverse.getRoot();
  }

  @Test
  public void testReverse3() {
    Solution reverse = new Solution();

    Solution.ListNode root = this.getLinkedList2();
    Solution.ListNode reverseNode = reverse.reserse2(root, 2, 2);
    // System.out.println("结果打印");
    reverse.printLinkedList(reverseNode);
    System.out.println();
    System.out.println("--------------------------------------------");
    System.out.println();
  }

  private Solution.ListNode getLinkedList2() {
    Solution reverse = new Solution();

    reverse.put(1);
    reverse.put(2);

    return reverse.getRoot();
  }
}
