package com.liujun.datastruct.base.search.skipList;

import java.util.Arrays;
import java.util.Random;

/**
 * 跳表的实现
 *
 * @author liujun
 * @version 0.0.1
 */
public class MySkipListInt {

  /** 当前最大层级 */
  private final int maxLevel;

  /** 随机函数 */
  private Random rand = new Random();

  /** 根节点 */
  private Node root;

  /** 当前的层级数 */
  private int levelNum;

  public MySkipListInt(int maxLevel) {
    this.maxLevel = maxLevel;
    this.root = new Node();
  }

  /**
   * 跳表插入节点
   *
   * @param value 数据
   */
  public void add(int value) {
    // 1,通过随机函数决定叶子节点数
    int level = this.randomLevel();
    // 节点信息
    Node tempNode = new Node();
    // 保存值
    tempNode.data = value;
    // 最大层级
    tempNode.nodeMaxLevel = level;
    // 跳表的当前层级数
    Node[] nodeForward = new Node[level];

    // 1,将跳表都指向头节点
    for (int i = 0; i < level; i++) {
      nodeForward[i] = root;
    }

    // 进行跳表的查找，找到每一层该插入的位置。记录到nodeForward中
    Node nodeTmp = root;
    for (int i = level - 1; i >= 0; i--) {
      // 从最顶层的节点开始
      while (nodeTmp.forward[i] != null && nodeTmp.forward[i].data < value) {
        nodeTmp = nodeTmp.forward[i];
      }
      // 设置当前跳表层的位置。
      nodeForward[i] = nodeTmp;
    }

    // 临时数组设置到跳表中
    for (int i = 0; i < level; i++) {
      tempNode.forward[i] = nodeForward[i].forward[i];
      nodeForward[i].forward[i] = tempNode;
    }

    // 设置当前跳表的层级
    if (levelNum < level) {
      this.levelNum = level;
    }
  }

  /**
   * 检查一个数是否存在于跳表中
   *
   * @param value 当前待查找的值
   * @return true 存在，false 不存在
   */
  public boolean find(int value) {
    Node node = root;
    // 按层级向下进行索引搜索
    for (int i = levelNum - 1; i >= 0; i--) {
      if (node.forward[i] != null && node.forward[i].data < value) {
        node = node.forward[i];
      }
    }

    if (node.forward[0] != null && node.forward[0].data == value) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 执行删除操作
   *
   * @param targetValue 目标值
   */
  public void delete(int targetValue) {
    // 1,找到每一层中的删除删除的前一个顶点
    Node[] levelNodes = new Node[maxLevel];
    Node tempNode = root;
    for (int i = maxLevel - 1; i >= 0; i--) {
      while (tempNode.forward[i] != null && tempNode.forward[i].data < targetValue) {
        tempNode = tempNode.forward[i];
      }
      levelNodes[i] = tempNode;
    }

    // 将每层中的结果进行移除操作
    if (tempNode.forward[0] != null && tempNode.forward[0].data == targetValue) {
      for (int i = maxLevel - 1; i >= 0; i--) {
        if (levelNodes[i].forward[i] != null && levelNodes[i].forward[i].data == targetValue) {
          levelNodes[i].forward[i] = levelNodes[i].forward[i].forward[i];
        }
      }
    }
  }

  /**
   * 随机生成跳表的层级
   *
   * <p>跳表使用随机函数，做到大致分布均匀。
   *
   * @return 跳表的层级
   */
  private int randomLevel() {
    int level = 1;
    for (int i = 1; i < maxLevel; i++) {
      if (rand.nextInt() % 2 == 0) {
        level++;
      }
    }
    return level;
  }

  /** 跳表的存储节点信息 */
  class Node {
    /** 用于存储数据,默认为-1 */
    private int data = -1;
    /** 跳表的节点 */
    private Node[] forward = new Node[maxLevel];
    /** 当前节点的最大层级 */
    private int nodeMaxLevel;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("data=").append(data);
      sb.append(", forward=").append(Arrays.toString(forward));
      sb.append(", nodeMaxLevel=").append(nodeMaxLevel);
      sb.append('}');
      return sb.toString();
    }
  }
}
