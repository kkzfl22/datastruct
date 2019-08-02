package com.liujun.datastruct.base.datastruct.linkedlist.implement;

/**
 * 实现一个循环链表
 *
 * <p>将最后一个节点的下一个节点指向头节点，即可实现循环
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/30
 */
public class MyLinkedLoop {

  /** 单链表的节点信息 */
  class Node {
    /** 当前值 */
    private int value;

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

  public MyLinkedLoop() {
    currNode = root;
    this.size = 0;
  }

  /**
   * 添加数据
   *
   * @param value
   */
  public void add(int value) {
    Node nodeTmp = new Node(value);

    nodeTmp.next = root;
    currNode.next = nodeTmp;
    currNode = nodeTmp;
    this.size++;
  }

  /**
   * 删除尾节点
   *
   * @return
   */
  public int deleteLast() {
    Node deleteNode = currNode;
    Node deleteParent = parentNode(deleteNode);
    int value = deleteNode.value;
    deleteParent.next = root;
    this.size--;
    currNode = deleteParent;
    return value;
  }

  public int size() {
    return size;
  }

  public boolean empty() {
    Node tmpNode = root.next;

    if (tmpNode == root) {
      return true;
    }

    return false;
  }

  /**
   * 进行头节点的删除
   *
   * @return
   */
  public int deleteTop() {
    Node rootNode = root;

    if (rootNode.next == null) {
      throw new IllegalArgumentException("root node is null ,can not delete");
    }

    int deletvalue = rootNode.next.value;
    // 找到根节点的下级节点
    Node nodeNext = rootNode.next.next;

    rootNode.next = nodeNext;

    this.size--;

    return deletvalue;
  }

  /**
   * 获取指定的位置
   *
   * @param index
   * @return
   */
  public int getValue(int index) {
    Node tmpNode = root.next;
    if (tmpNode == null) {
      return -1;
    }

    int loopIndex = 0;

    while (loopIndex < index) {
      tmpNode = tmpNode.next;

      if (tmpNode == null) {
        break;
      }
      loopIndex++;
    }

    if (tmpNode == null) {
      throw new IllegalArgumentException("over bounds");
    }

    return tmpNode.value;
  }

  /**
   * 找到父节点
   *
   * @param currNode 当前节点
   * @return 父级节点
   */
  private Node parentNode(Node currNode) {
    Node tmpNode = root;

    while (tmpNode.next != null) {
      if (tmpNode.next == currNode) {
        break;
      }
      tmpNode = tmpNode.next;
    }

    return tmpNode;
  }
}
