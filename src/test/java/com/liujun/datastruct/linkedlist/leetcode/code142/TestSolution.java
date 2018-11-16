package com.liujun.datastruct.linkedlist.leetcode.code142;

import com.liujun.datastruct.linkedlist.leetcode.LinkedListBase;
import org.junit.Test;

/**
 * 测试返回环形链表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class TestSolution {

  @Test
  public void hashCycle() {

    Solution linked = new Solution();
    linked.putNode(1);
    linked.putNode(2);
    linked.putNode(3);
    linked.putNode(4);
    linked.putNode(5);
    linked.putNode(6);
    linked.putNode(7);
    linked.putNode(8);
    linked.putNode(9);
    LinkedListBase.ListNode node = linked.finNode(8);
    LinkedListBase.ListNode node2 = linked.finNode(9);
    linked.putNextNode(node2, node);

    // linked.print(linked.root.next);
    //    linked.putNode(1);
    //    linked.putNode(2);

    // boolean cycle = linked.hasCycle(linked.root);
    LinkedListBase.ListNode cycle = linked.detectCycle(linked.root);
    // LinkedListBase.ListNode cycle = linked.detectCycle2(linked.root);

    System.out.println("结果是否存在环" + cycle.val);
  }
}
