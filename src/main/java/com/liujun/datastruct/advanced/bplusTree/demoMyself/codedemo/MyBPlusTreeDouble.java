package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自己来实现B+树索引的代码
 *
 * <p>基础的B+树的实现,再写一遍，加深印象
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/30
 */
public class MyBPlusTreeDouble {
  private final int DATA_NUM;
  private final int MEDIAN_NUM;
  private Node root;

  public MyBPlusTreeDouble(int dataNum) {
    this.DATA_NUM = dataNum - 1;
    this.MEDIAN_NUM = this.DATA_NUM / 2;
    root = new LeafNode();
  }

  public void insert(int key, String value) {
    root.insert(key, value);
  }

  public String get(int key) {
    return root.get(key);
  }

  public List<String> rangeQuery(int start, int end) {
    return root.rangeQuery(start, end);
  }

  public List<String> rangeQueryDesc(int start, int end) {
    return root.rangeQueryDesc(start, end);
  }

  public abstract class Node {
    protected List<Integer> keys = new ArrayList<>(DATA_NUM);

    public abstract Node spit();

    public abstract boolean isOverflow();

    public abstract int getLeafFirstKey();

    public abstract void insert(int key, String value);

    public abstract String get(int key);

    public abstract List<String> rangeQuery(int start, int end);

    public abstract List<String> rangeQueryDesc(int start, int end);
  }

  public class TreeNode extends Node {
    private List<Node> children = new ArrayList<>(DATA_NUM);

    @Override
    public Node spit() {

      List<Integer> allKeys = this.keys;
      List<Node> allChildren = this.children;

      this.keys = new ArrayList<>(allKeys.subList(0, MEDIAN_NUM));
      this.children = new ArrayList<>(allChildren.subList(0, MEDIAN_NUM + 1));

      // 叶子节点的分裂操作
      TreeNode rightNode = new TreeNode();
      rightNode.keys.addAll(allKeys.subList(MEDIAN_NUM, allKeys.size()));
      rightNode.keys.remove(0);
      rightNode.children.addAll(allChildren.subList(MEDIAN_NUM + 1, allChildren.size()));

      return rightNode;
    }

    @Override
    public boolean isOverflow() {
      return this.keys.size() >= DATA_NUM;
    }

    @Override
    public int getLeafFirstKey() {
      return this.children.get(0).getLeafFirstKey();
    }

    @Override
    public void insert(int key, String value) {
      Node children = this.getChildren(key);
      children.insert(key, value);

      // 检查子节点是否需要分裂
      if (children.isOverflow()) {
        // 如果子节点需要分裂操作，则直接进行分裂
        Node childrenRightNode = children.spit();

        // 将分裂后的节点，加入到当前节点中
        this.keys.add(childrenRightNode.getLeafFirstKey());
        this.children.add(childrenRightNode);
      }

      // 检查根节点是否到达分裂条件
      if (root.isOverflow()) {
        // 创建新的根节点
        TreeNode newRoot = new TreeNode();

        // 1，将当前的根节点进行分裂
        Node rootRightNode = root.spit();

        newRoot.keys.add(rootRightNode.getLeafFirstKey());
        newRoot.children.add(this);
        newRoot.children.add(rootRightNode);

        root = newRoot;
      }
    }

    private Node getChildren(int key) {
      // 1,在当前索引层中查找节点
      int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);
      int childIndex = 0;
      // 首先在索引层中插入数据
      if (searchIndex >= 0) {
        childIndex = searchIndex + 1;
      } else {
        childIndex = -searchIndex - 1;
      }

      // 在子节点中插入数据
      return this.children.get(childIndex);
    }

    @Override
    public String get(int key) {
      int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);

      int childIndex = 0;
      if (searchIndex >= 0) {
        childIndex = searchIndex + 1;
      } else {
        childIndex = -searchIndex - 1;
      }

      return children.get(childIndex).get(key);
    }

    @Override
    public List<String> rangeQuery(int start, int end) {

      return getChildren(start).rangeQuery(start, end);
    }

    @Override
    public List<String> rangeQueryDesc(int start, int end) {
      return getChildren(end).rangeQueryDesc(start, end);
    }
  }

  public class LeafNode extends Node {

    private List<String> value = new ArrayList<>(DATA_NUM);

    /** 链表的下一个节点 */
    private LeafNode next;

    /** 链表的上一个节点 */
    private LeafNode prev;

    @Override
    public Node spit() {
      LeafNode rightNode = new LeafNode();

      int subStartIndex = MEDIAN_NUM;
      int subEndIndex = this.keys.size();

      List<Integer> rightKeys = this.keys.subList(subStartIndex, subEndIndex);
      List<String> rightValue = this.value.subList(subStartIndex, subEndIndex);

      rightNode.keys.addAll(rightKeys);
      rightNode.value.addAll(rightValue);

      rightKeys.clear();
      rightValue.clear();

      // 下线指向修改
      rightNode.next = this.next;
      // 上级指向修改
      rightNode.prev = this;
      this.next = rightNode;

      return rightNode;
    }

    @Override
    public boolean isOverflow() {
      return this.keys.size() >= DATA_NUM;
    }

    @Override
    public int getLeafFirstKey() {
      return this.keys.get(0);
    }

    @Override
    public void insert(int key, String value) {
      int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);
      int insertIndex = 0;
      // 如果当前key已经存在，则覆盖数据
      if (searchIndex >= 0) {
        this.keys.set(searchIndex, key);
      } else {
        insertIndex = -searchIndex - 1;
        this.keys.add(insertIndex, key);
        this.value.add(insertIndex, value);
      }

      // 检查当前数据是否需要发生分裂
      if (root.isOverflow()) {
        Node rightNode = spit();

        // 构建新的根节点
        TreeNode newRoot = new TreeNode();
        newRoot.keys.add(rightNode.getLeafFirstKey());
        newRoot.children.add(this);
        newRoot.children.add(rightNode);

        root = newRoot;
      }
    }

    @Override
    public String get(int key) {
      int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);

      if (searchIndex >= 0) {
        return value.get(searchIndex);
      }

      return null;
    }

    @Override
    public List<String> rangeQuery(int start, int end) {
      int index = BinaryFunction.indexedBinarySearch(keys, start);
      if (index < 0) {
        return Collections.emptyList();
      }

      List<String> rangeList = new ArrayList<>();

      boolean gotoQuery = false;

      for (int i = index; i < keys.size(); i++) {
        if (keys.get(i) < end) {
          rangeList.add(value.get(i));
        } else {
          gotoQuery = true;
        }
      }

      // 如果仅在一个区间，不再继续向下一个节点查找
      if (gotoQuery) {
        return rangeList;
      }

      LeafNode nextNode = this.next;

      boolean breakFlag = false;

      while (nextNode != null) {
        for (int i = 0; i < keys.size(); i++) {
          if (nextNode.keys.get(i) < end) {
            rangeList.add(nextNode.value.get(i));
          } else {
            breakFlag = true;
            break;
          }
        }

        // 指向下一个节点
        nextNode = nextNode.next;

        if (breakFlag) {
          break;
        }
      }

      return rangeList;
    }

    @Override
    public List<String> rangeQueryDesc(int start, int end) {

      int searchIndex = BinaryFunction.indexedBinarySearch(keys, end);
      List<String> dataList = new ArrayList<>(end - start + 1);

      boolean gotoPrev = true;

      for (int i = searchIndex - 1; i >= 0; i--) {
        if (this.keys.get(i) >= start) {
          dataList.add(this.value.get(i));
        } else {
          gotoPrev = false;
          break;
        }
      }

      if (!gotoPrev) {
        return dataList;
      }

      LeafNode leafNode = this.prev;
      boolean prevBreak = false;
      while (leafNode != null) {
        for (int i = leafNode.keys.size() - 1; i >= 0; i--) {
          if (leafNode.keys.get(i) >= start) {
            dataList.add(leafNode.value.get(i));
          } else {
            prevBreak = true;
            break;
          }
        }
        leafNode = leafNode.prev;
        if (prevBreak) {
          break;
        }
      }

      return dataList;
    }
  }
}
