package com.liujun.datastruct.linkedlist;

/**
 * 循环链表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/13
 */
public class MyBothwayLinkedList {

  /** 链表的节点信息 */
  class Node {
    private Integer value;

    /** 指向上一个节点的指针 */
    private Node up;

    /** 指向下一个节点的指针 */
    private Node next;

    public Node(Integer value) {
      this.value = value;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("value=").append(value);
      sb.append('}');
      return sb.toString();
    }
  }

  /** 头节点 */
  private Node head = new Node(null);

  /** 当前节点，首次指向头头节 */
  private Node currNode = head;

  /**
   * 添加数据
   *
   * @param value 数据所对应的值
   */
  public void add(int value) {
    Node node = new Node(value);
    node.up = currNode;
    currNode.next = node;
    currNode = node;
  }

  /** 进行尾节点的移除操作 */
  public void removeLast() {
    currNode = currNode.up;
    currNode.next = null;
  }

  /**
   * 根据索引查找节点
   *
   * @param index 索引号
   */
  public Node findByIndex(int index) {
    Node temp = head;

    int ins = 0;
    while (temp.next != null) {
      temp = temp.next;

      if (index == ins) {
        return temp;
      }

      ins++;
    }

    return null;
  }

  /**
   * 根据值查找节点
   *
   * @param value 值
   * @return 节点信息
   */
  public Node findByValue(int value) {
    Node tmpNode = head;

    while (tmpNode.next != null) {
      tmpNode = tmpNode.next;

      if (tmpNode.value == value) {
        return tmpNode;
      }
    }

    return null;
  }

  /**
   * 在头节点前插入节点
   *
   * @param value
   */
  public void insertToHead(int value) {

    Node tmpHeadNode = new Node(value);

    Node oldFirstNode = head.next;
    tmpHeadNode.next = oldFirstNode;
    oldFirstNode.up = tmpHeadNode;

    head.next = tmpHeadNode;
  }

  /**
   * 在批定的节点后插入数据
   *
   * @param nodePoint 节点
   * @param value 插入的数据
   */
  public void insertToAfter(Node nodePoint, int value) {
    Node node = new Node(value);

    node.next = nodePoint.next;
    node.up = nodePoint;

    nodePoint.next = node;
  }

  /**
   * 在指定的节点前插入数据
   *
   * @param nodePoint 节点信息
   * @param value 插入值
   */
  public void insertToBefore(Node nodePoint, int value) {
    Node node = new Node(value);

    node.up = nodePoint.up;
    node.next = nodePoint;

    nodePoint.up.next = node;
  }

  /**
   * 进行指定节点的删除操作
   *
   * @param node 节点信息
   */
  public void deleteByNode(Node node) {
    node.up.next = node.next;
  }

  public void deleteByValue(int value) {
    Node node = this.findByValue(value);

    if (null != node) {
      node.up.next = node.next;
    }
  }

  /**
   * 以树形结构打印信息
   *
   * @param node 当前节点信息
   * @param index 深度
   */
  public void printTreeNode(Node node, int index) {
    Node printNode = head;

    if (null != node) {
      printNode = node;
    }

    if (index < 1) {
      index = 1;
    }

    if (null != printNode.value) {
      System.out.print("|");

      for (int i = 0; i < index; i++) {
        System.out.print("--");
      }
      System.out.println(printNode.value);
    }

    if (printNode.next != null) {
      this.printTreeNode(printNode.next, ++index);
    }
  }
}
