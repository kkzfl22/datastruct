package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code706;

/**
 * 706. 设计哈希映射
 *
 * <p>执行结果： 通过 显示详情 执行用时： 18 ms ,
 *
 * <p>在所有 Java 提交中击败了 94.22% 的用户
 * <p>内存消耗： 42.7 MB , 在所有 Java 提交中击败了 67.14% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class MyHashMap {

  /** 默认的map的大小 */
  private static final int DEFAULT_SIZE = 1024;

  private Node[] data;

  /** Initialize your data structure here. */
  public MyHashMap() {
    data = new Node[DEFAULT_SIZE];
  }

  /** value will always be non-negative. */
  public void put(int key, int value) {
    int dataIndex = key % DEFAULT_SIZE;
    Node dataRoot = data[dataIndex];
    // 首次加入
    if (dataRoot == null) {
      data[dataIndex] = new Node(null, null);
      data[dataIndex].next = new Node(key, value);
      return;
    }

    // 非首次的加入
    Node dataTmp = dataRoot;
    while (dataTmp.next != null) {
      if (dataTmp.next.key == key) {
        dataTmp.next.value = value;
        return;
      }
      dataTmp = dataTmp.next;
    }

    // 加入到尾节点中
    dataTmp.next = new Node(key, value);
  }

  /**
   * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping
   * for the key
   */
  public int get(int key) {
    int dataIndex = key % DEFAULT_SIZE;
    Node dataRoot = data[dataIndex];
    // 未设置值的情况
    if (dataRoot == null) {
      return -1;
    }
    // 已经设置的情况
    Node dataTmp = dataRoot;
    while (dataTmp.next != null) {
      if (dataTmp.next.key == key) {
        return dataTmp.next.value;
      }
      dataTmp = dataTmp.next;
    }

    return -1;
  }

  /** Removes the mapping of the specified value key if this map contains a mapping for the key */
  public void remove(int key) {
    int dataIndex = key % DEFAULT_SIZE;
    Node dataRoot = data[dataIndex];
    // 未设置值的情况
    if (dataRoot == null) {
      return;
    }
    // 已经设置的情况
    Node prevNode = null;
    Node dataTmp = dataRoot;
    while (dataTmp.next != null) {
      if (dataTmp.next.key == key) {
        prevNode = dataTmp;
        break;
      }
      dataTmp = dataTmp.next;
    }

    // 移除节点操作
    if (null != prevNode) {
      prevNode.next = prevNode.next.next;
    }
  }

  /** 节点信息 */
  private class Node {
    private Integer key;
    private Integer value;
    private Node next;

    public Node(Integer key, Integer value) {
      this.key = key;
      this.value = value;
    }
  }
}
