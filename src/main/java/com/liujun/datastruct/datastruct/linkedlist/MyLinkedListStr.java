package com.liujun.datastruct.datastruct.linkedlist;

/**
 * 自己实现一个简单的链表功能,即现为一个String的链表
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
public class MyLinkedListStr {

  /** 链表的节点信息 */
  class Node {
    private String value;

    private Node next;

    public Node(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
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
  public void add(String value) {
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
  public Node findByValue(String value) {
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

  public void insertToHead(String value) {

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
  public void insertToAfter(Node after, String value) {
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
  public void insertToBefore(Node before, String value) {
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
  public String removeLast() {

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
      String value = lastNode.value;

      lastNodeTop.next = null;

      return value;
    }

    return null;
  }

  public void deleteByValue(String value) {

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

  public void prStringTree(Node node, int index) {

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
      prStringTree(tempNode.next, index + 1);
    }
  }
}
