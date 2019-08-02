package com.liujun.datastruct.base.datastruct.linkedlist.leetcode.code23;

/**
 * 使用合并有序单链表方案类似归并排序中的合并操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/29
 */
public class SolutionRep {

  /** 单链表的节点信息 */
  public static class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
      val = x;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("ListNode{");
      sb.append("val=").append(val);
      sb.append('}');
      return sb.toString();
    }
  }

  public ListNode mergeKLists(ListNode[] lists) {

    // 声明一个int数组，记录链表的方问位置
    ListNode[] refArrays = new ListNode[lists.length];

    // 初始化第一次加载
    firstLoad(lists, refArrays);

    ListNode reslut = new ListNode(-1);
    ListNode currNode = reslut;

    BusiEntry minNode = null;

    // 进行数据写入操作
    while (!finish(refArrays)) {
      minNode = min(refArrays);

      if (null != minNode) {
        currNode.next = minNode.node;
        currNode = minNode.node;
        if (null != refArrays[minNode.nodeIndex]) {
          refArrays[minNode.nodeIndex] = refArrays[minNode.nodeIndex].next;
        }
        continue;
      }
      {
        break;
      }
    }

    return reslut.next;
  }

  /**
   * 检查当前是否结束
   *
   * @param refArrays 链表数组
   * @return
   */
  private boolean finish(ListNode[] refArrays) {
    boolean isfinish = true;

    for (ListNode node : refArrays) {
      if (null != node ) {
        isfinish = false;
        break;
      }
    }

    return isfinish;
  }

  private void firstLoad(ListNode[] lists, ListNode[] refArrays) {
    for (int i = 0; i < lists.length; i++) {
      refArrays[i] = lists[i];
    }
  }

  private BusiEntry min(ListNode[] refArrays) {
    int start = 0;

    ListNode minNode = null;
    ListNode nodeValue = null;

    for (int i = 0; i < refArrays.length; i++) {
      nodeValue = refArrays[i];

      // 如果当前队列为空，则跳过当前的操作，进行下一个操作
      if (null == nodeValue) {
        continue;
      }

      // 首次进行初始化
      if (minNode == null) {
        minNode = nodeValue;
        start = i;
        continue;
      }

      if (refArrays[i].val < minNode.val) {
        minNode = refArrays[i];
        start = i;
      }
    }

    if (minNode != null) {
      BusiEntry busi = new BusiEntry();

      busi.nodeIndex = start;
      busi.node = minNode;

      return busi;
    }

    return null;
  }

  class BusiEntry {

    private int nodeIndex;

    private ListNode node;
  }
}
