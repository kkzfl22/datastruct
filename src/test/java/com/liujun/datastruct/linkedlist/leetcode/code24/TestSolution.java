package com.liujun.datastruct.linkedlist.leetcode.code24;

import org.junit.Test;

/**
 * 测试两两交换链表中的节点
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class TestSolution {

  @Test
  public void putSwap() {

    Solution linked = new Solution();
    linked.putNode(1);
    linked.putNode(2);
    linked.putNode(3);
    linked.putNode(4);
    linked.putNode(5);
    linked.putNode(6);

    linked.print(linked.root.next);

    Solution.ListNode node = linked.swapPairs(linked.root.next);

    System.out.println("结果");
    linked.print(node);
  }
}
