package com.liujun.datastruct.datastruct.linkedlist.leetcode.code23;

import com.liujun.datastruct.datastruct.linkedlist.leetcode.LinkedListBase;
import org.junit.Test;

/**
 * 测试两两交换链表中的节点
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/15
 */
public class TestSolutionRecursion {

  @Test
  public void marge() {

    SolutionRecursion linked = new SolutionRecursion();
    linked.putNode(1);
    linked.putNode(2);
    linked.putNode(3);
    linked.putNode(8);

    SolutionRecursion linked2 = new SolutionRecursion();
    linked2.putNode(4);
    linked2.putNode(5);
    linked2.putNode(6);

    LinkedListBase.ListNode node = linked.root;
    LinkedListBase.ListNode node2 = linked2.root;
    LinkedListBase.ListNode[] marge = new LinkedListBase.ListNode[] {node.next, node2.next};

    LinkedListBase.ListNode result = linked.mergeKLists(marge);

    System.out.println("结果");
    linked.print(result);
  }

  @Test
  public void marge2() {

    SolutionRecursion linked = new SolutionRecursion();
    linked.putNode(1);
    linked.putNode(2);
    linked.putNode(3);

    SolutionRecursion linked2 = new SolutionRecursion();
    linked2.putNode(4);
    linked2.putNode(5);
    linked2.putNode(6);

    LinkedListBase.ListNode node = linked.root;
    LinkedListBase.ListNode node2 = linked2.root;
    LinkedListBase.ListNode[] marge = new LinkedListBase.ListNode[] {node.next, node2.next};

    LinkedListBase.ListNode result = linked.mergeKLists(marge);

    System.out.println("结果");
    linked.print(result);
  }

  @Test
  public void marge3() {

    SolutionRecursion linked = new SolutionRecursion();
    linked.putNode(1);
    linked.putNode(4);
    linked.putNode(5);

    SolutionRecursion linked2 = new SolutionRecursion();
    linked2.putNode(1);
    linked2.putNode(3);
    linked2.putNode(4);

    SolutionRecursion linked3 = new SolutionRecursion();
    linked3.putNode(2);
    linked3.putNode(6);
    linked3.putNode(90);

    SolutionRecursion linked4 = new SolutionRecursion();
    linked4.putNode(2);
    linked4.putNode(6);
    linked4.putNode(90);

    LinkedListBase.ListNode node = linked.root;
    LinkedListBase.ListNode node2 = linked2.root;
    LinkedListBase.ListNode[] marge =
        new LinkedListBase.ListNode[] {node.next, node2.next, linked3.root.next, linked4.root.next};

    LinkedListBase.ListNode result = linked.mergeKLists(marge);

    System.out.println("结果");
    linked.print(result);
  }
}
