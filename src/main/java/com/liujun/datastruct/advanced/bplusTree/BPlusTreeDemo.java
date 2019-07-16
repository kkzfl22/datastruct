package com.liujun.datastruct.advanced.bplusTree;

import com.liujun.datastruct.utils.UnsafeInstance;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/20
 */
public class BPlusTreeDemo {

  /** 根节点 */
  private BplusTreeLeafNode root;

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

    /** m =5 表示为4叉树索引 */
    private static final int m = 5;

    /** 键值用来划分数据区间 */
    public int[] keywords = new int[m - 1];

    /** 保存的数据子节点指针 */
    public BplusTreeNode[] children = new BplusTreeNode[m];
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

    /** 数据键值 */
    public int[] keyworkd = new int[k];

    /** 数据的地址信息 */
    public long[] dataAddress = new long[k];
    /** 这个节点在链表中的前驱节点 */
    public BplusTreeLeafNode prev;

    /** 这个节点在链表中的后继节点 */
    public BplusTreeLeafNode next;
  }

  public void add(int value) {}

  public static void main(String[] args) {

    System.out.println(UnsafeInstance.UNSAFEINSTANCE.pageSize());
    System.out.println(UnsafeInstance.UNSAFEINSTANCE.pageSize());
    System.out.println(UnsafeInstance.UNSAFEINSTANCE.pageSize());
    System.out.println(UnsafeInstance.UNSAFEINSTANCE.pageSize());

    Field[] fields = BplusTreeLeafNode.class.getDeclaredFields();

    Unsafe UNSAFE;

    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");

      field.setAccessible(true);

      UNSAFE = (Unsafe) field.get(null);

    } catch (Exception e) {
      e.printStackTrace();
      throw new Error();
    }

    for (Field field : fields) {
      System.out.println(field.getName());
      System.out.println(field.getName() + "---offSet:" + UNSAFE.objectFieldOffset(field));
    }
  }
}
