package com.liujun.datastruct.datastruct.linkedlist.leetcode.code141;

import com.liujun.datastruct.datastruct.linkedlist.leetcode.LinkedListBase;
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
  public void hashCycle() {

    Solution linked = new Solution();
    linked.putNode(1);
    linked.putNode(2);
    linked.putNode(3);
    linked.putNode(4);
    linked.putNode(5);
    linked.putNode(6);
    LinkedListBase.ListNode node = linked.finNode(1);
    LinkedListBase.ListNode node2 = linked.finNode(6);
    // linked.putNextNode(node2, node);

    // linked.print(linked.root.next);
    //    linked.putNode(1);
    //    linked.putNode(2);

    // boolean cycle = linked.hasCycle(linked.root);
    boolean cycle = linked.hasCycle2(linked.root);

    System.out.println("结果是否存在环" + cycle);
  }
}
