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

  public void setCircle(int startIndex, int lastVast) {

    Node node = this.findByValue(90);
    Node nodeLast = this.findByValue(100);
    nodeLast.next = node;
  }

  /**
   * 使用快慢指针来检查节点环
   *
   * @param node 需要检查的节点
   * @return true 存在环，false 不存在环
   */
  public boolean checkCircle(Node node) {
    if (null == node) {
      node = headNode;
    }

    int index = 0;

    Node quicNode = node.next;
    Node slowNode = node;

    while (quicNode != null && quicNode.next != null) {
      quicNode = quicNode.next.next;
      slowNode = slowNode.next;

      ++index;

      if (quicNode == slowNode) {
        System.out.println("quic slow point index:" + index);
        return true;
      }
    }

    return false;
  }

  public Node margeLinked(Node firstNode, Node twoNode) {
    Node tmpOneNode = firstNode;
    Node tmpTwoNode = twoNode;

    Node head = null;
    // 1,找到头节点,第一个节点的值，小于第二链表的节点值，则以第一个节点为头节点
    if (tmpOneNode.value < tmpTwoNode.value) {
      head = tmpOneNode;
      tmpOneNode = tmpOneNode.next;
    }
    // 否则采用第二个节点为头节点
    else {
      head = tmpTwoNode;
      tmpTwoNode = tmpTwoNode.next;
    }

    Node currNode = head;

    // 遍历两个链表，将接链表的大小进行组合
    while (tmpOneNode != null && tmpTwoNode != null) {
      // 当链表1中的节点，小于链表2中的节点时，则加入新到新的节点中
      if (tmpOneNode.value < tmpTwoNode.value) {
        currNode.next = tmpOneNode;
        tmpOneNode = tmpOneNode.next;
      } else {
        currNode.next = tmpTwoNode;
        tmpTwoNode = tmpTwoNode.next;
      }
      currNode = currNode.next;
    }

    // 当链表遍历完成后，则将剩余的链表组合到返回的链表中
    if (null != tmpOneNode) {
      currNode.next = tmpOneNode;
    } else if (null != twoNode) {
      currNode.next = tmpTwoNode;
    }

    return head;
  }

  public Node deleteLastKth(Node list, int k) {
    Node fast = list;
    int i = 1;
    while (fast != null && i < k) {
      fast = fast.next;
      ++i;
    }

    if (fast == null) return list;

    Node slow = list;
    Node prev = null;
    while (fast.next != null) {
      fast = fast.next;
      prev = slow;
      slow = slow.next;
    }

    if (prev == null) {
      list = list.next;
    } else {
      prev.next = prev.next.next;
    }
    return list;
  }

  /**
   * 删除倒数第index个元素
   *
   * @param node 节点
   * @param index 索引
   * @return 删除后的列表
   */
  public Node deleteLastIndex(Node node, int index) {
    // 1,在单链表中要删除倒数第多少个元素，需要先找到正数的节点
    Node first = node;

    int tmpIndex = 0;
    while (first != null && tmpIndex < index) {
      first = first.next;
      ++tmpIndex;
    }

    if (null == first) {
      return null;
    }

    Node prev = null;
    Node slow = node;

    while (first != null) {
      first = first.next;
      prev = slow;
      slow = slow.next;
    }

    if (prev == null) {
      node = node.next;
    } else {
      prev.next = prev.next.next;
    }

    return node;
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

  /**
   * 求中间节点，同样使用快慢指针，快指针是慢指针的2倍速度，当快指针遍历到尾部时，慢指针遍历一半，这个解法非常的精炒
   *
   * @param node
   * @return
   */
  public Node findMidNode(Node node) {
    Node first = node;
    Node mid = node;
    while (first.next != null && first.next.next != null) {
      first = first.next.next;
      mid = mid.next;
    }

    return mid;
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
