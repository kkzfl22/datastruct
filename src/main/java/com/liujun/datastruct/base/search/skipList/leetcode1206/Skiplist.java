package com.liujun.datastruct.base.search.skipList.leetcode1206;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Skiplist {

  private final int maxlevel = 4;

  /** 根节点 */
  private Node root = new Node(maxlevel);

  /** 随机函数 */
  private Random rand = ThreadLocalRandom.current();

  /** 当前的层级 */
  private int levelCount;

  public Skiplist() {}

  /**
   * 返回target是否存在于跳表中。
   *
   * @param target 目标数
   * @return
   */
  public boolean search(int target) {

    Node tempSearch = root;
    // 自顶向下搜索
    for (int i = levelCount - 1; i >= 0; i--) {
      while (tempSearch.forward[i] != null && tempSearch.forward[i].value < target) {
        tempSearch = tempSearch.forward[i];
      }
    }

    if (tempSearch.forward[0] != null && tempSearch.forward[0].value == target) {
      return true;
    }

    return false;
  }

  /**
   * 插入一个元素到跳表。
   *
   * @param num 数值
   */
  public void add(int num) {
    int randLevel = randomNum();
    Node node = new Node(randLevel);
    node.value = num;
    node.levelNum = randLevel;
    // 找到跳表的插入点
    Node[] levelNode = new Node[randLevel];
    // 每层节点初始化为root节点
    for (int i = 0; i < randLevel; i++) {
      levelNode[i] = root;
    }
    Node tmpNode = root;
    // 从最顶层向下遍历
    for (int i = randLevel - 1; i >= 0; i--) {
      // 进行节点查找
      while (tmpNode.forward[i] != null && tmpNode.forward[i].value < num) {
        tmpNode = tmpNode.forward[i];
      }
      levelNode[i] = tmpNode;
    }

    // 将数据插入到跳表中
    for (int i = 0; i < randLevel; i++) {
      node.forward[i] = levelNode[i].forward[i];
      levelNode[i].forward[i] = node;
    }

    // 设置层级
    if (levelCount < randLevel) {
      levelCount = randLevel;
    }
  }

  /**
   * 随机函数，也是跳表比较好的一个设计，能做最平均情况下接近最好
   *
   * @return
   */
  private int randomNum() {
    int level = 1;
    for (int i = 1; i < maxlevel; i++) {
      if (rand.nextInt() % 2 == 0) {
        level++;
      }
    }
    return level;
  }

  /**
   * 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
   *
   * @param num 数值
   * @return
   */
  public boolean erase(int num) {
    // 将数据进行查找然后进宪删除操作
    Node[] prefixNode = new Node[levelCount];
    Node searchNode = root;
    // 自顶向下搜索
    for (int i = levelCount - 1; i >= 0; i--) {
      while (searchNode.forward[i] != null && searchNode.forward[i].value < num) {
        searchNode = searchNode.forward[i];
      }
      prefixNode[i] = searchNode;
    }

    // 按层执行节点的删除操作
    if (searchNode.forward[0] != null && searchNode.forward[0].value == num) {
      // 从顶层向下，一层一层的删除
      for (int i = levelCount - 1; i >= 0; i--) {
        if (prefixNode[i].forward[i] != null && prefixNode[i].forward[i].value == num) {
          prefixNode[i].forward[i] = prefixNode[i].forward[i].forward[i];
        }
      }
      return true;
    }

    return false;
  }

  /** 跳表的节点信息 */
  private class Node {
    /** 当前跳表的值 */
    private int value;
    /** 跳表的高度层节点 */
    private Node[] forward;
    /** 跳表的的高度 */
    private int levelNum;

    public Node(int levelNum) {
      this.levelNum = levelNum;
      this.forward = new Node[levelNum];
    }
  }
}
