package com.liujun.datastruct.advanced.bplusTree.demoMyself;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自己来实现B+树索引的代码
 *
 * <p>基础的B+树的实现，目前已经支持添加key的查找
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/30
 */
public class MyBPlusTreeBase {

  /** 树形节点大小 */
  public static final int NODE_SIZE = 5;

  public static final int UPPER_SIZE = NODE_SIZE - 1;

  public static final int OVER_FLOW_SIZE = UPPER_SIZE / 2;

  /** 根节点 */
  private Node root;

  public MyBPlusTreeBase() {
    this.root = new LeftNode();
  }

  public void insert(int key, String value) {
    this.root.insert(key, value);
  }

  public String get(int key) {
    return root.get(key);
  }

  public List<String> rangQuery(int start, int end) {
    return root.rangeQuery(start, end);
  }

  public abstract class Node {

    /** 索引的的信息 */
    protected List<Integer> keys = new ArrayList<>(NODE_SIZE);

    /**
     * 数据插入方法
     *
     * @param key
     * @param value
     */
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

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public abstract String get(int key);

    /**
     * 区间范围的查找
     *
     * @param start
     * @param end
     * @return
     */
    public abstract List<String> rangeQuery(int start, int end);
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
        Node rightNode = childNode.spit();
        // 将分裂的节点挂入原节点中
        this.insertChildNode(key, rightNode);
      }

      if (root.isOverFlow()) {

        TreeNode newRoot = new TreeNode();
        Node rightNode = spit();
        newRoot.keys.add(rightNode.getLeafFirstKey());
        newRoot.child.add(this);
        newRoot.child.add(rightNode);

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
      return this.keys.size() >= UPPER_SIZE;
    }

    @Override
    public Node spit() {

      TreeNode rightNode = new TreeNode();

      List<Integer> allKeys = this.keys;
      List<Node> allChild = this.child;

      int medianIndex = OVER_FLOW_SIZE;

      this.keys = new ArrayList<>(allKeys.subList(0, medianIndex));
      this.child = new ArrayList<>(allChild.subList(0, medianIndex + 1));

      rightNode.keys.addAll(allKeys.subList(medianIndex, allKeys.size()));
      rightNode.keys.remove(0);
      rightNode.child.addAll(allChild.subList(medianIndex + 1, allChild.size()));

      return rightNode;
    }

    @Override
    public int getLeafFirstKey() {
      return this.child.get(0).getLeafFirstKey();
    }

    public Node getChild(int key) {
      // 查找当前的值在索引层的位置
      int searchKey = Collections.binarySearch(keys, key);
      // 存在两种可能性，数据在索引层，或者不在索引层
      // 如果数据在索引层，则直接按索引+1向后查找即可，因为在索引层移除了首个节点。
      int keyIndex = -1;
      if (searchKey >= 0) {
        keyIndex = searchKey + 1;
      }
      // 如果索引层找不到此数据，此返回的是一个负值的可能插入数据的位置，通过再取负值，即可计算出位置。
      else {
        keyIndex = -searchKey - 1;
      }

      return child.get(keyIndex);
    }

    @Override
    public String get(int key) {
      return getChild(key).get(key);
    }

    /**
     * 索引层中查找此区间值
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<String> rangeQuery(int start, int end) {
      return getChild(start).rangeQuery(start, end);
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
        LeftNode rightNode = (LeftNode) spit();

        TreeNode newRoot = new TreeNode();
        newRoot.keys.add(rightNode.getLeafFirstKey());
        newRoot.child.add(this);
        newRoot.child.add(rightNode);

        root = newRoot;
      }
    }

    @Override
    public boolean isOverFlow() {
      return this.keys.size() >= UPPER_SIZE;
    }

    @Override
    public Node spit() {

      LeftNode rightNode = new LeftNode();

      // 进行叶子节点的分裂操作，分裂一半出去
      int from = (keys.size()) / 2;
      int to = keys.size();

      List<Integer> subKey = keys.subList(from, to);
      List<String> subValues = values.subList(from, to);

      rightNode.keys.addAll(subKey);
      rightNode.values.addAll(subValues);

      subKey.clear();
      subValues.clear();

      // 分裂时串连指针
      rightNode.next = this.next;
      this.next = rightNode;

      return rightNode;
    }

    @Override
    public int getLeafFirstKey() {
      return this.keys.get(0);
    }

    @Override
    public String get(int key) {

      // 在key中查找索引所在的位置
      int searchIndex = Collections.binarySearch(keys, key);

      if (searchIndex >= 0) {
        return values.get(searchIndex);
      }

      return null;
    }

    @Override
    public List<String> rangeQuery(int start, int end) {

      // 1,找到当前层的开始节点
      int startIndex = Collections.binarySearch(keys, start);

      if (startIndex >= 0) {
        List<String> result = new ArrayList<>(end - start + 1);

        for (int i = startIndex; i < keys.size(); i++) {
          result.add(values.get(i));
        }

        LeftNode dataCurrNode = this;
        while (dataCurrNode.next != null) {

          dataCurrNode = dataCurrNode.next;

          boolean gotoFlag = true;

          for (int i = 0; i < dataCurrNode.keys.size(); i++) {
            if (dataCurrNode.keys.get(i) < end) {
              result.add(dataCurrNode.values.get(i));
            } else {
              gotoFlag = false;
              break;
            }
          }

          if (!gotoFlag) {
            break;
          }
        }
        return result;
      }

      return Collections.emptyList();
    }
  }
}
