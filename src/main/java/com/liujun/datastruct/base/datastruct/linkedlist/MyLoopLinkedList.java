package com.liujun.datastruct.base.datastruct.linkedlist;

/**
 * 自己实现一个简单的循环链表的实现，使用int类型,代码中添加两个节点，头尾两个节点，可以方便的操作
 *
 * <p>，链表的插入
 *
 * <p>2，链表的删除
 *
 * <p>3，链表的查找
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/11
 */
public class MyLoopLinkedList {

  /** 链表的节点信息 */
  class Node {
    private Integer value;

    private Node next;

    public Node(Integer value) {
      this.value = value;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("MyNode{");
      sb.append("value=").append(value);
      sb.append('}');
      return sb.toString();
    }
  }

  /** 头链表信息 */
  private Node headNode = new Node(null);

  /** 尾节点，因为是循环链表，所以链表的尾节点指定头节点 */
  private Node lastNode = headNode;

  /**
   * 进行循环链表尾节点的添加操作
   *
   * @param value 需要插入的值的信息
   */
  public void add(int value) {
    Node node = new Node(value);
    // 将原来链表尾节点指向当前节点
    lastNode.next = node;
    // 将最后的节点指向当前节点
    lastNode = node;
    // 最后一个节点的下一个节点指向头头点，形成一个环
    lastNode.next = headNode;
  }

  /** 进行尾节点的删除操作 */
  public void removeLast() {
    // 1,要删除尾节点就必须要找到尾节点的上一级节点
    Node findNode = headNode;

    while (findNode.next != lastNode) {
      findNode = findNode.next;
    }

    // 然后直接将上一级节点的下一个节点改为头节点，需可成为一个循环链表
    lastNode = findNode;
    findNode.next = headNode;
  }

  /**
   * 根据索引获取指定链表的值
   *
   * @param index 索引
   */
  public Node findIndex(int index) {
    Node tmpNode = headNode.next;

    int i = 0;

    while (tmpNode.next != headNode) {

      if (index == i) {
        return tmpNode;
      }

      tmpNode = tmpNode.next;
      ++i;
    }

    return null;
  }

  /**
   * 将当前节点插入头
   *
   * @param value 当前值信息
   */
  public void insertToHead(int value) {
    Node node = new Node(value);

    // 首先将插入的节点指向原来的头节点
    node.next = headNode.next;

    // 然后再将当前的头节点，指向当前创建的节点
    headNode.next = node;
  }

  /**
   * 根据值查找节点信息
   *
   * @param value 当前的值信息
   * @return 节点信息
   */
  public Node findByValue(int value) {
    Node tempFinNode = headNode;

    while (tempFinNode.next != headNode) {
      if (null != tempFinNode.value && tempFinNode.value == value) {
        return tempFinNode;
      }

      tempFinNode = tempFinNode.next;
    }

    return null;
  }

  /**
   * 在指定节点前插入值
   *
   * @param node 节点信息
   * @param value 插入的值信息
   */
  public void insertToBefore(Node node, int value) {
    Node inserNode = new Node(value);

    // 找到节点的上一级节点
    Node tempFind = this.findByNodeTop(node);

    // 然后插入节点指向原节点所指向的后节点
    inserNode.next = tempFind.next;
    tempFind.next = inserNode;
  }

  /**
   * 找到节点的上一级节点
   *
   * @return 节点信息
   */
  private Node findByNodeTop(Node node) {
    // 1,找到节点的上一个节点
    Node tempFind = headNode;
    while (tempFind.next != headNode && tempFind.next != node) {
      tempFind = tempFind.next;
    }

    return tempFind;
  }

  /**
   * 在指定的节点后插入值
   *
   * @param currNode 当前节点
   * @param value 值信息
   */
  public void insertToAfter(Node currNode, int value) {
    Node inserNode = new Node(value);

    inserNode.next = currNode.next;
    currNode.next = inserNode;
  }

  /**
   * 删除指定的节点
   *
   * @param node 指定的节点信息
   */
  public void deleteByNode(Node node) {

    // 找到节点的上一级节点
    Node findNodeTop = this.findByNodeTop(node);

    // 将上一级节点的next直接指向node的next，即可删除当前节点
    if (node != headNode) {
      findNodeTop.next = node.next;
    }
  }

  /**
   * 删除值所对应的节点信息
   *
   * @param value 值信息
   */
  public void deleteByValue(int value) {
    // 1,找到值所对应的节点
    Node valueNode = this.findByValue(value);

    if (null != valueNode) {

      // 找到节点的上一级节点
      Node findNodeTop = this.findByNodeTop(valueNode);

      // 然后将节点进行删除
      findNodeTop.next = valueNode.next;
    }
  }

  /**
   * 统计当前链表的大小
   *
   * @return 当前链表的大小
   */
  public int size() {
    Node sizeNode = headNode;

    int size = 0;
    while (sizeNode.next != headNode) {
      ++size;
      sizeNode = sizeNode.next;
    }

    return size;
  }

  /**
   * 使用树形结构打印信息
   *
   * @param node
   * @param index
   */
  public void printTreeNode(Node node, int index) {
    Node printNode = node;

    if (null == printNode) {
      printNode = headNode;
    }

    if (index < 1) {
      index = 1;
    }

    // 当存在值时才打印链表
    if (printNode.value != null) {

      System.out.print("|");
      for (int i = 0; i < index; i++) {
        System.out.print("--");
      }
      System.out.println(printNode.value);
    }

    if (printNode != null && printNode.next != headNode) {
      this.printTreeNode(printNode.next, ++index);
    }
  }
}
