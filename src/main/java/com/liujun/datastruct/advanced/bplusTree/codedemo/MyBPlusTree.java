package com.liujun.datastruct.advanced.bplusTree.codedemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自己来实现B+树索引的代码
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/30
 */
public class MyBPlusTree {

  /** 树形节点大小 */
  public static final int NODE_SIZE = 4;

  /** 根节点 */
  private Node root;

  public MyBPlusTree() {
    this.root = new LeftNode();
  }

  public void insert(int key, String value) {
    this.root.insert(key, value);
  }

  public abstract class Node {

    /** 索引的的信息 */
    protected List<Integer> keys = new ArrayList<>(NODE_SIZE);

    public abstract void insert(int key, String value);

    /**
     * 是否已经达到了分裂的条件
     *
     * @return
     */
    public abstract boolean isOverFlow();

    /**
     * 进行节点的分裂操作
     *
     * @return
     */
    public abstract Node spit();

    /**
     * 获取叶子节点的首key
     *
     * @return
     */
    public abstract int getLeafFirstKey();
  }

  /** B+树的索引层节点信息 */
  public class TreeNode extends Node {

    protected List<Node> child = new ArrayList<>();

    @Override
    public void insert(int key, String value) {
      // 进行索引层节点的查找
      Node childNode = getChild(key);
      // 节点插入数据
      childNode.insert(key, value);

      // 检查子节点是否需要分裂操作
      if (childNode.isOverFlow()) {
        // 进行索引节点的分裂操作
        Node childNodeSpit = childNode.spit();
        // 将分裂的节点挂入原节点中
        this.insertChildNode(key, childNodeSpit);
      }

      if (root.isOverFlow()) {

        TreeNode newRoot = new TreeNode();

        Node node = spit();

        newRoot.keys.add(node.getLeafFirstKey());
        newRoot.child.add(this);
        newRoot.child.add(node);

        root = newRoot;
      }
    }

    public void insertChildNode(int key, Node childNode) {
      // 1,根据key查找所在的位置
      int searchIndex = Collections.binarySearch(keys, key);
      int operIndex = searchIndex >= 0 ? searchIndex + 1 : -searchIndex - 1;

      if (searchIndex >= 0) {
        child.set(operIndex, childNode);
      } else {
        this.keys.add(childNode.getLeafFirstKey());
        this.child.add(childNode);
      }
    }

    @Override
    public boolean isOverFlow() {
      return this.child.size() > NODE_SIZE;
    }

    @Override
    public Node spit() {

      TreeNode newNode = new TreeNode();

      int from = this.keys.size() / 2 + 1;
      int to = this.keys.size();

      List<Integer> subKey = this.keys.subList(from, to);
      List<Node> subNodeList = this.child.subList(from, to);

      newNode.keys.addAll(subKey);
      newNode.keys.remove(from - 1);
      newNode.child.addAll(subNodeList);

      return newNode;
    }

    @Override
    public int getLeafFirstKey() {
      return 0;
    }

    public Node getChild(int key) {
      int searchKey = Collections.binarySearch(keys, key);

      int keyIndex = searchKey >= 0 ? searchKey + 1 : -searchKey - 1;

      return child.get(keyIndex);
    }
  }

  /** 叶子节点的信息 */
  public class LeftNode extends Node {

    /** 值相关的信息 */
    private List<String> values = new ArrayList<>(NODE_SIZE);

    /** 下一级节点的索引 */
    private LeftNode next;

    @Override
    public void insert(int key, String value) {
      // 在key中查找索引所在的位置
      int searchIndex = Collections.binarySearch(keys, key);

      if (searchIndex >= 0) {
        values.set(searchIndex, value);
      } else {
        // 通过计算得到插入位置
        int arrIndex = searchIndex >= 0 ? searchIndex : -searchIndex - 1;

        this.keys.add(key);
        this.values.add(arrIndex, value);
      }

      // 检查root节点是否需要分裂操作
      if (root.isOverFlow()) {
        // 1,进行分裂
        Node spit = spit();

        TreeNode newRoot = new TreeNode();
        newRoot.keys.add(spit.getLeafFirstKey());
        newRoot.child.add(this);
        newRoot.child.add(spit);

        root = newRoot;
      }
    }

    @Override
    public boolean isOverFlow() {
      return this.keys.size() > NODE_SIZE - 1;
    }

    @Override
    public Node spit() {

      LeftNode spitNode = new LeftNode();

      // 进行叶子节点的分裂操作，分裂一半出去
      int from = (keys.size() + 1) / 2;
      int to = keys.size();

      List<Integer> subKey = keys.subList(from, to);
      List<String> subValues = values.subList(from, to);

      spitNode.keys.addAll(subKey);
      spitNode.values.addAll(subValues);

      subKey.clear();
      subValues.clear();

      return spitNode;
    }

    @Override
    public int getLeafFirstKey() {
      return this.keys.get(0);
    }
  }
}
