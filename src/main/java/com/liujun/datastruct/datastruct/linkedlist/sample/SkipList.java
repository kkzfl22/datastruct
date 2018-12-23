package com.liujun.datastruct.datastruct.linkedlist.sample;

import java.util.*;

/**
 * 跳表的一种实现方法。 跳表中存储的是正整数，并且存储的是不重复的。
 *
 * <p>Author：ZHENG
 */
public class SkipList {

  private static final int MAX_LEVEL = 4;

  private int levelCount = 1;

  private Node head = new Node(); // 带头链表

  private Random r = new Random();

  public Node find(int value) {
    Node p = head;
    for (int i = levelCount - 1; i >= 0; --i) {
      while (p.forwards[i] != null && p.forwards[i].data < value) {
        p = p.forwards[i];
      }
    }

    if (p.forwards[0] != null && p.forwards[0].data == value) {
      return p.forwards[0];
    } else {
      return null;
    }
  }

  public void insert(int value) {
    int level = randomLevel();
    Node newNode = new Node();
    newNode.data = value;
    newNode.maxLevel = level;
    Node update[] = new Node[level];

    // 将所有的层级节点都指向头节点
    for (int i = 0; i < level; ++i) {
      update[i] = head;
    }

    Node p = head;
    for (int i = level - 1; i >= 0; --i) {

      // 找到比当前插入值小的节点的forward
      while (p.forwards[i] != null && p.forwards[i].data < value) {
        p = p.forwards[i];
      }
      // 将每一层的节点，都指向比插入值小的节点
      update[i] = p;
    }

    // 遍历每层链表，将新的节点添加到链中
    for (int i = 0; i < level; ++i) {
      //
      newNode.forwards[i] = update[i].forwards[i];
      update[i].forwards[i] = newNode;
    }

    if (levelCount < level) levelCount = level;
  }

  public void delete(int value) {
    Node[] update = new Node[levelCount];
    Node p = head;
    for (int i = levelCount - 1; i >= 0; --i) {
      while (p.forwards[i] != null && p.forwards[i].data < value) {
        p = p.forwards[i];
      }
      update[i] = p;
    }

    if (p.forwards[0] != null && p.forwards[0].data == value) {
      for (int i = levelCount - 1; i >= 0; --i) {
        if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
          update[i].forwards[i] = update[i].forwards[i].forwards[i];
        }
      }
    }
  }

  private int randomLevel() {
    int level = 1;
    for (int i = 1; i < MAX_LEVEL; ++i) {
      if (r.nextInt() % 2 == 1) {
        level++;
      }
    }

    return level;
  }

  public void printAll() {
    Node p = head;
    while (p != null) {
      System.out.print(p.forwards[0] + " ");
      p = p.forwards[0];
    }
    System.out.println();
  }

  public void printAllNodeTree() {
    TreeMap<Integer, List<Node>> levelNodeMap = new TreeMap<>();

    Node p = head;

    List<Node> listNode = null;

    while (p.forwards[0] != null) {
      p = p.forwards[0];
      List<Node> levNodeList = levelNodeMap.get(p.maxLevel);

      if (null == levNodeList) {
        levNodeList = new ArrayList<>();
      }
      levNodeList.add(p);

      levelNodeMap.put(p.maxLevel, levNodeList);
    }

    Iterator<Map.Entry<Integer, List<Node>>> entryIter = levelNodeMap.entrySet().iterator();

    Map.Entry<Integer, List<Node>> entry = null;

    while (entryIter.hasNext()) {
      entry = entryIter.next();
      System.out.println("level:" + entry.getKey() + ",value:" + entry.getValue());
    }
  }

  public class Node {
    // 数据
    private int data = -1;
    // 跳表节点
    private Node forwards[] = new Node[MAX_LEVEL];
    private int maxLevel = 0;

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("{ data: ");
      builder.append(data);
      builder.append("; levels: ");
      builder.append(maxLevel);
      builder.append(" }");

      return builder.toString();
    }
  }
}
