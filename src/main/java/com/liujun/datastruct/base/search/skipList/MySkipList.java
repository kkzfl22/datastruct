package com.liujun.datastruct.base.search.skipList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自己动手实现一个跳表
 *
 * <p>使用泛型
 *
 * @author liujun
 * @version 0.0.1
 */
public class MySkipList<T> {

  private final int maxLevel;

  /** 随机数 */
  private Random random = ThreadLocalRandom.current();

  /** 根节点 */
  private Node root;

  /** 当前层级 */
  private int currLevel;

  public MySkipList(int maxLevel) {
    this.maxLevel = maxLevel;
    this.root = new Node();
  }

  /**
   * 插入一个节点
   *
   * @param data 数据
   */
  public void insert(T data) {
    Node<T> tmpNode = new Node<>();
    tmpNode.data = data;
    int tempCurrLevel = getRandomLevel();
    tmpNode.levelNum = tempCurrLevel;

    Node<T>[] tmpNextNodes = new Node[tmpNode.levelNum];

    // 找到每一层节点的下一个节点
    Node<T> tmpNodeItem = root;
    for (int i = tempCurrLevel - 1; i >= 0; i--) {
      while (tmpNodeItem.next[i] != null
          && ((Comparable) tmpNodeItem.next[i].data).compareTo(data) == -1) {
        tmpNodeItem = tmpNodeItem.next[i];
      }

      tmpNextNodes[i] = tmpNodeItem;
    }

    // 将当前节点加入到跳表中
    for (int i = 0; i < tempCurrLevel; i++) {
      tmpNode.next[i] = tmpNextNodes[i].next[i];
      tmpNextNodes[i].next[i] = tmpNode;
    }

    if (currLevel < tempCurrLevel) {
      this.currLevel = tempCurrLevel;
    }
  }

  private int getRandomLevel() {
    int level = 1;
    for (int i = 1; i < maxLevel; i++) {
      if (random.nextInt() % 2 == 0) {
        level++;
      }
    }
    return level;
  }

  /**
   * 删除一个元素
   *
   * @param data 元素的内容
   */
  public void delete(T data) {
    Node<T>[] tmpNextNodes = new Node[currLevel];

    Node<T> tmpNodeItem = root;
    for (int i = currLevel - 1; i >= 0; i--) {
      while (tmpNodeItem.next[i] != null
          && ((Comparable) tmpNodeItem.next[i].data).compareTo(data) == -1) {
        tmpNodeItem = tmpNodeItem.next[i];
      }

      tmpNextNodes[i] = tmpNodeItem;
    }

    // 进行节点的删除操作
    if (null != tmpNodeItem.next[0] && tmpNodeItem.next[0].data.equals(data)) {
      // 进行节点的删除操作
      for (int i = currLevel - 1; i >= 0; i--) {
        if (tmpNextNodes[i].next[i] != null
            && ((Comparable) tmpNextNodes[i].next[i].data).compareTo(data) == 0) {
          tmpNextNodes[i].next[i] = tmpNextNodes[i].next[i].next[i];
        }
      }
    }
  }

  /**
   * 查找一个元素
   *
   * @param data 数据信息
   * @return
   */
  public Node find(T data) {

    // 进行节点的查找
    Node<T> tmpNode = root;

    for (int i = maxLevel - 1; i >= 0; i--) {
      while (tmpNode.next[i] != null && ((Comparable) tmpNode.next[i].data).compareTo(data) == -1) {
        tmpNode = tmpNode.next[i];
      }
    }

    if (tmpNode.next[0].data.equals(data)) {
      return tmpNode.next[0];
    }

    return null;
  }

  /**
   * 按范围搜索
   *
   * @param start
   * @param end
   * @return
   */
  public List<T> scopeData(T start, T end) {

    Node<T> startNode = this.find(start);

    List<T> resultList = new ArrayList<>();

    while (startNode != null && ((Comparable) startNode.data).compareTo(end) == -1) {
      resultList.add(startNode.data);
      startNode = startNode.next[0];
    }

    return resultList;
  }

  /** 执行迭代输出 */
  private class SkipListIterator implements Iterator<T> {

    /** 根节点 */
    private Node<T> dataNode;

    public SkipListIterator(Node<T> dataNode) {
      this.dataNode = dataNode;
    }

    @Override
    public boolean hasNext() {
      return dataNode.next[0] != null;
    }

    @Override
    public T next() {
      T dataValue = dataNode.next[0].data;

      dataNode = dataNode.next[0];

      return dataValue;
    }
  }

  /**
   * 进行迭代顺的输出操作
   *
   * @return 迭代信息
   */
  public Iterator<T> getIterator() {
    return new SkipListIterator(root);
  }

  /** 跳表的节点 */
  private class Node<T> {

    /** 数据信息 */
    private T data;

    /** 跳表的节点 */
    private Node<T>[] next = new Node[maxLevel];

    /** 当前跳表的层级 */
    private int levelNum;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("data=").append(data);
      sb.append(", forward=").append(Arrays.toString(next));
      sb.append(", levelNum=").append(levelNum);
      sb.append('}');
      return sb.toString();
    }
  }
}
