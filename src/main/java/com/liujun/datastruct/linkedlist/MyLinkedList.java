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

    public Node(Integer value, Node next) {
      this.value = value;
      this.next = next;
    }

    public Integer getValue() {
      return value;
    }
  }

  /** 头链表信息 */
  private Node headNode = new Node(null, null);

  /** 当前节点信息 */
  private Node currNode = headNode;

  /**
   * 添加节点信息
   *
   * @param value
   */
  public void add(int value) {
    Node node = new Node(value, null);
    currNode.next = node;
    currNode = node;
  }

  /**
   * 移除尾节点
   *
   * @return
   */
  public Integer removeLast() {
    // 1,移除除节点，首先需要找到尾节点的上一个节点
    Node temNode = headNode;
    while (temNode.next != null) {
      if (temNode.next == currNode) {
        break;
      } else {
        temNode = temNode.next;
      }
    }

    // 然后检查是否为头节点，头节点固定，不能删除
    if (currNode != headNode) {

      Integer value = temNode.next.value;
      temNode.next = null;
      currNode = temNode;
      return value;
    } else {
      return null;
    }
  }
}
