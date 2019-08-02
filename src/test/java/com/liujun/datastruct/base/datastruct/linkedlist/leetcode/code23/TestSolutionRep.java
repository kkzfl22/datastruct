package com.liujun.datastruct.base.datastruct.linkedlist.leetcode.code23;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试合并K个有序链表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/01
 */
public class TestSolutionRep {

  @Test
  public void testSolution() {
    SolutionRep.ListNode[] list = new SolutionRep.ListNode[3];

    list[0] = this.getList(1, 2, 6);
    list[1] = this.getList(3, 4, 9);
    list[2] = this.getList(5, 7, 8);

    SolutionRep instance = new SolutionRep();
    SolutionRep.ListNode listRsp = instance.mergeKLists(list);
    print(listRsp);
    Assert.assertNotEquals(null, listRsp);
  }

  private SolutionRep.ListNode getList(int valu1, int value2, int val3) {
    SolutionRep.ListNode one = new SolutionRep.ListNode(valu1);
    SolutionRep.ListNode two = new SolutionRep.ListNode(value2);
    SolutionRep.ListNode third = new SolutionRep.ListNode(val3);
    one.next = two;
    two.next = third;

    return one;
  }

  private void print(SolutionRep.ListNode node) {
    SolutionRep.ListNode tmpeNode = node;

    while (tmpeNode != null) {
      System.out.print("-->" + tmpeNode.val + "\t");
      tmpeNode = tmpeNode.next;
    }
  }
}
