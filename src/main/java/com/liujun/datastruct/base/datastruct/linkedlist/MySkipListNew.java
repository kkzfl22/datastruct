package com.liujun.datastruct.base.datastruct.linkedlist;

import java.util.Random;

/**
 * 跳表的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/08
 */
public class MySkipListNew {

  /** 最大层级 */
  private static final int MAX_LEVEL = 8;

  /** 随机数生成对象 */
  private Random rand = new Random();

  /** 队头节点信息 */
  private Node head = new Node();

  /** 当前最大的层级 */
  private int curMaxLevel = 1;

  class Node {

    /** 节点的值 */
    private int value;

    /** 后续链子节点 */
    private Node[] forwords = new Node[MAX_LEVEL];

    /** 最大层级 */
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
   * 插入一个数据
   *
   * @param value
   */
  public void insert(int value) {

    // 获得层级
    int level = this.getRandomLevel();

    Node newNode = new Node();
    newNode.value = value;
    newNode.maxLevel = level;

    Node[] nodeIndex = new Node[level];

    // 1,初始化所有索引节点为头节点
    for (int i = 0; i < level; i++) {
      nodeIndex[i] = head;
    }

    Node findNode = head;

    // 2,遍历层级，从最顶层向下遍历，查找比当前小的节点
    for (int i = level - 1; i >= 0; i--) {
      // 查找比当前小的节点
      while (findNode.forwords[i] != null && findNode.forwords[i].value < value) {
        findNode = findNode.forwords[i];
      }
      // 找到当前需插入的后面的节点
      nodeIndex[i] = findNode;
    }

    // 3,按层级将当前的节点插入到索引层级中
    for (int i = level - 1; i >= 0; i--) {
      newNode.forwords[i] = nodeIndex[i].forwords[i];
      nodeIndex[i].forwords[i] = newNode;
    }

    if (level > curMaxLevel) {
      curMaxLevel = level;
    }
  }

  /**
   * 查询节点，
   *
   * @param value 值信息
   * @return 节点信息
   */
  public Node findNode(int value) {

    Node findNode = head;

    for (int i = curMaxLevel - 1; i >= 0; i--) {
      while (findNode.forwords[i] != null && findNode.forwords[i].value < value) {
        findNode = findNode.forwords[i];
      }
    }

    // 做最后时间点检查
    if (findNode.forwords[0] != null && findNode.forwords[0].value == value) {
      return findNode.forwords[0];
    } else {
      return null;
    }
  }

  /**
   * 删除节点操作
   *
   * @param value 值信息
   */
  public void delete(int value) {
    // 声明一个索引层的数组，用于接收新的删除节点的后续节点信息
    Node[] nodeTop = new Node[MAX_LEVEL];

    // 1，要删除节点，需要找到删除节点的前驱节点
    Node findeTopNode = head;
    for (int i = curMaxLevel - 1; i >= 0; i--) {
      while (findeTopNode.forwords[i] != null && findeTopNode.forwords[i].value < value) {
        findeTopNode = findeTopNode.forwords[i];
      }
      nodeTop[i] = findeTopNode;
    }

    // 然后将节点直接指向后续节点，完成删除操作
    for (int i = curMaxLevel - 1; i >= 0; i--) {
      // 检查当前的后级节点不能为null,为null说明为最后一个节点不能直接等于下一个节点
      if (nodeTop[i].forwords[i] != null) {
        nodeTop[i].forwords[i] = nodeTop[i].forwords[i].forwords[i];
      }
    }
  }

  /** 按层级打印数据 */
  public void printTreeNode() {

    Node printNode = head;
    for (int i = curMaxLevel; i >= 0; i--) {
      System.out.print("level:" + i + ",value{");
      while (printNode != null) {
        System.out.print(printNode.value + ",");
        printNode = printNode.forwords[i];
      }
      System.out.println("}");
      printNode = head;
    }
  }

  /**
   * 获得一个随机的层级信息，用作索引
   *
   * @return 层级
   */
  private int getRandomLevel() {
    int level = 1;

    for (int i = 1; i < MAX_LEVEL; i++) {
      if (rand.nextInt() % 2 == 1) {
        level++;
      }
    }

    return level;
  }
}
