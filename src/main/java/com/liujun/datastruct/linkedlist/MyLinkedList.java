package com.liujun.datastruct.linkedlist;

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
    headNode.next = node;
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
   * 在指定节点的前面插入节点
   *
   * @param before 之前的节点信息
   * @param newNode 新的节点信息
   */
  private void insertToBefore(Node before, Node newNode) {
    Node tmpHead = headNode;

    // 如果当前为头节点，则需要特殊处理，不能直接插入到头

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

    // 再次找到尾节点的上一个节点
    Node lastNodeTop = headNode;

    while (lastNodeTop != null && lastNodeTop.next != lastNode) {
      lastNodeTop = lastNodeTop.next;
    }

    //如果上一个节点为头节点，不能删除
    if(lastNode == headNode)
    {
      return null;
    }

    //然后再将节点进行修改，将尾节点的上一个节点指向空
    Integer resultValue = lastNode.value;
    lastNodeTop.next = null;


    return resultValue;


  }
}
