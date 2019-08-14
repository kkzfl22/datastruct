package com.liujun.datastruct.base.datastruct.linkedlist.implement.cache;

/**
 * 使用链表来实现一个lru，最近最少使用策略的缓存
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/10
 */
public class LRULinked {

  private class Node {

    private int val;

    private Node next;

    public Node(int val) {
      this.val = val;
    }
  }

  /** 根节点 */
  private Node root = new Node(-1);

  private Node currNode = root;

  private final int maxLength;

  private int nodeSize = 0;

  public LRULinked(int maxLength) {
    this.maxLength = maxLength;
  }

  public void push(int value) {
    if (nodeSize + 1 > maxLength) {
      throw new IndexOutOfBoundsException("node more maxlength :" + maxLength);
    }

    Node nodePush = new Node(value);
    currNode.next = nodePush;
    currNode = nodePush;
    nodeSize++;
  }

  public int size() {
    return nodeSize;
  }

  /**
   * 弹出指定值的节点
   *
   * @param value
   */
  public void popSpecify(int value) {
    Node tmpNodeParent = root;
    Node tmpNodeChild = root.next;

    boolean popRsp = false;

    while (tmpNodeChild != null) {
      if (value == tmpNodeChild.val) {
        tmpNodeParent.next = tmpNodeChild.next;
        popRsp = true;
        break;
      }
      tmpNodeChild = tmpNodeChild.next;
    }

    if (popRsp) {
      nodeSize--;
    }
  }

  /** 进行头节点的弹出操作 */
  public void popHead() {
    Node tmpNodeHead = root.next;
    // 进行头节点的弹出操作
    if (tmpNodeHead != null) {
      root.next = tmpNodeHead.next;
      nodeSize--;
    }
  }
}
