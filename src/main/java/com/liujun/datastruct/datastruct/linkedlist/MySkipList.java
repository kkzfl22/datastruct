package com.liujun.datastruct.datastruct.linkedlist;

import java.util.Random;

/**
 * 跳表的实现
 *
 * <p>1,跳表是一种插入，删除、查找都非常高效的动态结构，使用链表作为底层的存储结构
 *
 * <p>2，跳表的插入，删除，查询的时间复杂度为O(logn)，是一种用链表实现类似二分查找的性能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/07
 */
public class MySkipList {

  /** 跳表的索引层级 */
  private static final int MAX_LEVEL = 4;

  /** 头节点信息 */
  private Node head = new Node();

  /** 随机函数 */
  private Random random = new Random();

  /** 当前的最大层级 */
  private int currMaxLevel;

  /** 存储的节点信息 */
  class Node {

    /** 存储的数据值信息 */
    private int value;

    /** 跳表的索引层 */
    private Node[] forward = new Node[MAX_LEVEL];

    /** 当前节点的最大层级 */
    private int maxLevel;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("value=").append(value);
      sb.append(", maxLevel=").append(maxLevel);
      sb.append('}');
      return sb.toString();
    }
  }

  /**
   * 在跳表中插入一节节点
   *
   * @param value 待插入的值
   */
  public void insert(int value) {
    // 1,获得跳表的层级
    int level = this.getRandomLevel();

    Node nodeNode = new Node();
    nodeNode.value = value;
    nodeNode.maxLevel = level;

    Node[] nodeIndex = new Node[level];

    // 初始化节点，为头节点
    for (int i = 0; i < level; i++) {
      nodeIndex[i] = head;
    }

    Node inserNode = head;

    // 找到当前节点在各层中的位置, 从顶层开始遍历
    for (int i = level - 1; i >= 0; i--) {
      // 找到当前节点在当前层中的位置
      while (inserNode.forward[i] != null && inserNode.forward[i].value < value) {
        inserNode = inserNode.forward[i];
      }
      nodeIndex[i] = inserNode;
    }

    // 将当前的节点，插入到多层索引链表中
    for (int i = 0; i < level; i++) {
      nodeNode.forward[i] = nodeIndex[i].forward[i];
      nodeIndex[i].forward[i] = nodeNode;
    }

    if (currMaxLevel < level) {
      currMaxLevel = level;
    }
  }

  /** 按层级打印节点信息 */
  public void printTreeNode() {
    Node printNode = head;
    for (int i = MAX_LEVEL - 1; i >= 0; i--) {
      System.out.print("level:" + i + ",value{");
      while (printNode != null) {
        System.out.print(printNode.value + ",");
        printNode = printNode.forward[i];
      }
      System.out.println("}");
      printNode = head;
    }
  }

  /**
   * 随机生成跳表的层级
   *
   * @return
   */
  public int getRandomLevel() {
    int level = 1;
    for (int i = 1; i < MAX_LEVEL; i++) {
      if (random.nextInt() % 2 == 1) {
        level++;
      }
    }
    return level;
  }

  /**
   * 在跳表中查询节点
   *
   * @param value 要查找的值
   * @return 节点
   */
  public Node find(int value) {

    Node findNode = head;

    for (int i = currMaxLevel - 1; i >= 0; i--) {

      while (findNode.forward[i] != null && findNode.forward[i].value < value) {
        findNode = findNode.forward[i];
      }
    }

    if (findNode.forward[0] != null && findNode.forward[0].value == value) {
      return findNode;
    } else {
      return null;
    }
  }

  /**
   * 删除节点
   *
   * @param value 待删除的值
   */
  public void delete(int value) {
    Node delNode = head;

    // 记录下每层索引的位置
    Node[] findNode = new Node[currMaxLevel];

    for (int i = currMaxLevel - 1; i >= 0; i--) {
      // 找到当前节点的后一个节点
      while (delNode.forward[i] != null && delNode.forward[i].value < value) {
        delNode = delNode.forward[i];
      }
      findNode[i] = delNode;
    }

    // 当节点查询成功后 进行节点的删除操作
    if (delNode.forward[0] != null && delNode.forward[0].value == value) {
      // 将每一层节点中节点更新，踢除当前多余的元素,删除时需要从高层向低层一级一级删除
      for (int i = currMaxLevel - 1; i >= 0; i--) {
        // 进行节点的删除时，需要进行检查，因为有些节点子存在最低层节点
        if (findNode[i].forward[i] != null) {
          findNode[i].forward[i] = findNode[i].forward[i].forward[i];
        }
      }
    }
  }
}
