package com.liujun.datastruct.advanced.bplusTree.codedemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/20
 */
public class BPlusTreeDemo {

  /**
   * B+树 非叶子节点的定义
   *
   * <p>假如 keywordss = [3,5,8,10]
   *
   * <p>4个键值分别为5个区间(-INF,3),(3,5),(5,8),(8,10),(10,INF)
   *
   * <p>5个区间分别对应:children[0]...children[4]
   *
   * <p>m是事先计算好的，计算依据是让所有信息的大小正好等于页的大小：
   *
   * <p>PAGE_SIZE = (m-1)*4[keywords的大小]+m*8[childred大小]
   */
  public class BplusTreeNode {

    public static final int m = 5; // 5 叉树
    public int[] keywords = new int[m - 1]; // 键值，用来划分数据区间
    public BplusTreeNode[] children = new BplusTreeNode[m]; // 保存子节点指针
  }

  /**
   * 这是B+树叶子节点的定义
   *
   * <p>B+树中叶子节点跟内部节点是不一样的 叶子节点存储的是值，而非区间 这个定义里，每个叶子节点，存储3个数据行的键值及地址信息。
   *
   * <p>K是事先计算得到的，计算依据是让所有的信息的大小正好等于页的大小:
   *
   * <p>PAGE_SIZE = k*4[keyw...大小]+k*8[dataaddress大小]+8*[prev大小]+8*[next大小]
   */
  public class BplusTreeLeafNode {

    public static final int k = 3;
    public int[] keywords = new int[k]; // 数据的键值
    public long[] dataAddress = new long[k]; // 数据地址

    public BplusTreeLeafNode prev; // 这个结点在链表中的前驱结点
    public BplusTreeLeafNode next; // 这个结点在链表中的后继结点
  }
}
