package com.liujun.datastruct.linkedlist.leetcode.code25;

import com.liujun.datastruct.linkedlist.leetcode.LinkedListBase;
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
  public void reverseKGroup() {

    Solution linked = new Solution();
    linked.putNode(1);
    linked.putNode(2);
    linked.putNode(3);
    linked.putNode(4);
    linked.putNode(5);
    linked.putNode(6);
    linked.putNode(7);

    // linked.print(linked.root.next);
    //    linked.putNode(1);
    //    linked.putNode(2);

    // boolean cycle = linked.hasCycle(linked.root);
    Solution.ListNode cycle = linked.reverseKGroup(linked.root.next, 5);

    linked.print(cycle);
  }
}
