package com.liujun.datastruct.base.datastruct.linkedlist.implement;

/**
 * 实现一个双端的队列
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/30
 */
public class MyLinkedTwoNode {

  /** 单链表的节点信息 */
  class Node {
    /** 当前值 */
    private int value;

    /** 上一个节点 */
    private Node top;

    /** 下一个节点 */
    private Node next;

    public Node(int value) {
      this.value = value;
    }
  }

  /** 根节点 */
  private Node root = new Node(-1);

  /** 添加节点 */
  private Node currNode;

  /** 链表的大小 */
  private int size;

  public MyLinkedTwoNode() {
    currNode = root;
    this.size = 0;
  }

  /**
   * 添加数据
   *
   * @param value
   */
  public void add(int value) {
    Node tmpAdd = new Node(value);
    tmpAdd.top = currNode;
    currNode.next = tmpAdd;
    currNode = tmpAdd;
    size++;
  }

  /**
   * 删除尾节点
   *
   * @return
   */
  public int deleteLast() {
    Node tmpParentNode = currNode.top;

    int delValue = -1;

    if (tmpParentNode.next != null) {
      delValue = currNode.value;
      tmpParentNode.next = null;
      currNode = tmpParentNode;
    }
    return delValue;
  }

  public int size() {
    return size;
  }

  /**
   * 进行头节点的删除
   *
   * @return
   */
  public int deleteTop() {
    Node delNodeRoot = root.next;

    int value = -1;

    if (delNodeRoot != null) {
      Node nexNode = delNodeRoot.next;
      value = delNodeRoot.value;
      root.next = nexNode;
    }

    return value;
  }

  public boolean empty() {
    if (root.next == null) {
      return true;
    }
    return false;
  }

  /**
   * 获取指定的位置
   *
   * @param index
   * @return
   */
  public int getValue(int index) {
    Node rootNode = root.next;
    int indexTmp = 0;
    Node finNode = rootNode;

    while (indexTmp < index) {
      if (rootNode.next != null) {
        rootNode = rootNode.next;
        finNode = rootNode;
      } else {
        finNode = null;
        break;
      }
      indexTmp++;
    }

    if (null != finNode) {
      return finNode.value;
    }

    return -1;
  }
}
