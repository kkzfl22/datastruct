package com.liujun.datastruct.linkedlist;

import java.util.HashMap;

/**
 * 自己实现一个简单的链表功能,即现为一个int的链表
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
public class MyLinkedList {

  /** 链表的节点信息 */
  class Node {
    private Integer value;

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

  /** 头链表信息 */
  private Node headNode = new Node(null);

  /**
   * 添加节点信息
   *
   * @param value
   */
  public void add(int value) {
    Node node = new Node(value);

    Node headNodeAdd = headNode;

    // 1，找到尾节点，然后插入
    while (headNodeAdd.next != null) {
      headNodeAdd = headNodeAdd.next;
    }

    headNodeAdd.next = node;
  }

  /**
   * 根据值查找节点
   *
   * @param value 传递的值信息
   * @return 节点信息
   */
  public Node findByValue(int value) {
    Node temNode = headNode.next;

    while (temNode != null) {
      if (temNode.value == value) {
        return temNode;
      } else {
        temNode = temNode.next;
      }
    }

    return null;
  }

  /**
   * 根据索引查找节点
   *
   * @param index 索引信息
   * @return 节点信息
   */
  public Node findByIndex(int index) {
    if (index < 0) {
      return null;
    }

    Node temp = headNode.next;

    int tmpIndex = 0;

    while (tmpIndex != index && temp != null) {
      temp = temp.next;
      ++tmpIndex;
    }

    return temp;
  }

  public void insertToHead(int value) {

    Node insNode = new Node(value);

    this.insertToHead(insNode);
  }

  /**
   * 插入节点操作
   *
   * @param node 需要插入的节点信息
   */
  private void insertToHead(Node node) {
    if (node != null) {
      Node tmp = headNode.next;
      headNode.next = node;
      node.next = tmp;
    }
  }

  /**
   * 在指定节点的后面插入数节点
   *
   * @param after 指定的节点信息
   * @param value 指定的节点值
   */
  public void insertToAfter(Node after, int value) {
    Node newValue = new Node(value);
    this.insertToAfter(after, newValue);
  }

  /**
   * 在指定节点插入后
   *
   * @param after
   * @param newNode
   */
  private void insertToAfter(Node after, Node newNode) {
    newNode.next = after.next;
    after.next = newNode;
  }

  /**
   * 在指定节点前插入值
   *
   * @param before 指定的前节点
   * @param value 需要插入的值
   */
  public void insertToBefore(Node before, int value) {
    Node node = new Node(value);
    this.insertToBefore(before, node);
  }

  /**
   * 在指定节点的前面插入节点
   *
   * @param before 之前的节点信息
   * @param newNode 新的节点信息
   */
  private void insertToBefore(Node before, Node newNode) {
    Node tmpHead = headNode;

    // 1，找到指定节点的前一个节点
    while (null != tmpHead && tmpHead.next != before) {
      tmpHead = tmpHead.next;
    }

    newNode.next = tmpHead.next;
    tmpHead.next = newNode;
  }

  /**
   * 移除尾节点
   *
   * @return
   */
  public Integer removeLast() {

    // 1,移除尾节点，需要从头节点开始遍历找到尾节点
    Node lastNode = headNode;

    while (lastNode != null && lastNode.next != null) {
      lastNode = lastNode.next;
    }

    // 找到尾节点的前一个节点
    Node lastNodeTop = headNode;
    while (null != lastNodeTop && lastNodeTop.next != lastNode) {
      lastNodeTop = lastNodeTop.next;
    }

    if (headNode != lastNode) {
      int value = lastNode.value;

      lastNodeTop.next = null;

      return value;
    }

    return null;
  }

  public void deleteByValue(int value) {

    Node tempNode = headNode;

    // 只存在首节点，不删除，直接返回，其他从数据首节点开始
    if (null != tempNode.next) {
      tempNode = tempNode.next;
    } else {
      return;
    }

    // 1，找到对应值的节点
    while (tempNode != null && tempNode.value != value) {
      tempNode = tempNode.next;
    }

    // 为null，说明未找到该节点
    if (tempNode != null) {
      // 找到当前节点的上一级节点
      Node tempNodeTop = headNode;
      while (tempNodeTop != null && tempNodeTop.next != tempNode) {
        tempNodeTop = tempNodeTop.next;
      }

      // 将值的上一级节点，修改为删除值的下一级节点
      tempNodeTop.next = tempNode.next;
    }
  }

  public void reverse() {
    Node head = null, current = headNode.next, currNext = null;

    while (current != null) {
      currNext = current.next;

      current.next = head;
      head = current;
      current = currNext;
    }

    headNode.next = head;
  }

  public void setCircle() {
    Node tempLoad = headNode.next;

    while (tempLoad.next != null) {
      tempLoad = tempLoad.next;
    }

    // 设置环
    tempLoad.next = headNode;
  }

  /**
   * 检查环
   *
   * @return true 存在环，false 不存在环
   */
  public boolean checkCircle() {
    Node first = headNode.next.next;
    Node slow = headNode.next;

    while (first != null && first.next != null) {
      first = first.next.next;
      slow = slow.next;

      if (first == slow) {
        return true;
      }
    }

    return false;
  }

  /**
   * 单链表反转
   *
   * @param list
   * @return
   */
  public static Node reverse(Node list) {
    Node headNode = null;

    Node previousNode = null;
    Node currentNode = list;
    while (currentNode != null) {
      Node nextNode = currentNode.next;
      if (nextNode == null) {
        headNode = currentNode;
      }
      currentNode.next = previousNode;
      previousNode = currentNode;
      currentNode = nextNode;
    }

    return headNode;
  }

  public void printTree(Node node, int index) {

    Node tempNode = null;

    if (null == node) {
      tempNode = headNode;
    } else {
      tempNode = node;
    }

    if (null != tempNode) {

      System.out.print("|");
      for (int i = 0; i < index; i++) {
        System.out.print("--");
      }

      System.out.println(tempNode.value);
    }

    if (null != tempNode.next) {
      printTree(tempNode.next, index + 1);
    }
  }
}
