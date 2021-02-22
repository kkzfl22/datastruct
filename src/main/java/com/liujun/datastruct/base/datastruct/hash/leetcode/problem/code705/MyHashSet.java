package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code705;

/**
 * leetcode 705 设计哈希集合
 *
 * <p>执行结果：通过 显示详情 执行用时：17 ms,
 *
 * <p>在所有 Java 提交中击败了84.27%的用户
 *
 * <p>内存消耗：45 MB, 在所有 Java 提交中击败了79.14%的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class MyHashSet {

  /** 默认数据存储的大小 */
  private static final int DEFAULT_SIZE = 1024;

  /** 数据集合 */
  private Node[] data;

  /** Initialize your data structure here. */
  public MyHashSet() {
    data = new Node[DEFAULT_SIZE];
  }

  public void add(int key) {
    int dataIndex = key % DEFAULT_SIZE;
    Node dataRoot = data[dataIndex];
    // 如果为首个节点，直接设置
    if (dataRoot == null) {
      // 根节点
      data[dataIndex] = new Node(null);
      data[dataIndex].next = new Node(key);
    } else {
      // 后续节点，需要遍历
      Node nodeTmp = dataRoot;
      while (nodeTmp.next != null) {
        if (nodeTmp.next.key == key) {
          return;
        }
        nodeTmp = nodeTmp.next;
      }
      // 最后没有找到，则添加到最后
      nodeTmp.next = new Node(key);
    }
  }

  public void remove(int key) {
    int dataIndex = key % DEFAULT_SIZE;
    Node dataRoot = data[dataIndex];
    if (dataRoot == null) {
      return;
    } else {
      // 其他节点的处理
      Node prevNode = null;
      Node nodeTmp = dataRoot;
      while (nodeTmp.next != null) {
        if (nodeTmp.next.key == key) {
          prevNode = nodeTmp;
          break;
        }
        nodeTmp = nodeTmp.next;
      }

      if (prevNode != null) {
        prevNode.next = prevNode.next.next;
      }
    }
  }

  /** Returns true if this set contains the specified element */
  public boolean contains(int key) {
    int dataIndex = key % DEFAULT_SIZE;
    Node dataRoot = data[dataIndex];
    if (dataRoot == null) {
      return false;
    } else {
      // 其他节点的处理
      Node nodeTmp = dataRoot.next;
      while (nodeTmp != null) {
        if (nodeTmp.key == key) {
          return true;
        }
        nodeTmp = nodeTmp.next;
      }
    }
    return false;
  }

  /** 创建哈希的节点信息 */
  private class Node {

    /** key信息 */
    private Integer key;

    /** 下一个节点 */
    private Node next;

    public Node(Integer key) {
      this.key = key;
    }
  }
}
