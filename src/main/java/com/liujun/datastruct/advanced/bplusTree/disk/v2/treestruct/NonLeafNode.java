package com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct;

import com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo.BinaryFunction;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.constant.NodeTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liujun
 * @since 2022/11/24
 */
public class NonLeafNode extends Node {

  /** 节点类型信息 */
  private NodeTypeEnum nodeType;

  /** 当前的位置 */
  private long position;

  /** 中位数节点 */
  private final int midNonLeafNum;

  /** 索引的数据 */
  private final List<Integer> keys;

  /** 子节点的地址信息 */
  public final List<Long> childrenAddress;

  /** 根节点信息 */
  private BPlusTree tree;

  public NonLeafNode(BPlusTree tree) {

    this.midNonLeafNum = tree.getLeafNonNum() / 2;
    this.keys = new ArrayList<>(tree.getLeafNonNum() - 1);
    this.tree = tree;
    // 子节点地址信息
    this.childrenAddress = new ArrayList<>(tree.getLeafNonNum());
    // 标识节点类型
    nodeType = NodeTypeEnum.NON_LEAF;
  }

  @Override
  public void add(Integer key, Map<String, Object> value) {
    // 在子节点中插入
    Node children = getChildren(key);
    children.add(key, value);

    // 检查叶子节点是否到达分裂条件
    if (children.isOverFlow()) {
      Node childrenRightNode = children.spit();

      // 对子节点引用的修改，需要做保存操作
      tree.getChunkStream().writeChunk(children, children.getPosition());

      this.keys.add(childrenRightNode.getFirstKey());
      this.getChildrenAddress().add(childrenRightNode.getPosition());
      // 将当前节点的数据进行保存操作
      tree.getChunkStream().writeChunk(this, this.getPosition());
    }

    // 进行叶子节点的root节点分裂
    if (tree.getRoot().isOverFlow()) {
      // 进行分裂操作
      Node rootRightNode = this.spit();
      // 当根节点发生分裂后，需要将当前节点最新信息存储操作
      tree.getChunkStream().writeChunk(this, this.position);

      NonLeafNode newRoot = new NonLeafNode(tree);
      newRoot.keys.add(rootRightNode.getFirstKey());

      newRoot.getChildrenAddress().add(this.getPosition());
      newRoot.getChildrenAddress().add(rootRightNode.getPosition());

      tree.setRoot(newRoot);
    }
  }

  /**
   * 获取索引所对应的子节点
   *
   * @param key
   * @return
   */
  private Node getChildren(Integer key) {
    // 进行树枝节点的操作，此部分在于查找叶子节点
    int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);
    int childIndex;
    if (searchIndex >= 0) {
      childIndex = searchIndex + 1;
    } else {
      childIndex = -searchIndex - 1;
    }
    // return this.children.get(childIndex);
    long childrenPosition = this.getChildrenAddress().get(childIndex);

    // 1,读取子节点的信息
    return tree.getChunkStream().readerChunk(childrenPosition);
  }

  @Override
  public Map<String, Object> get(Integer key) {
    return getChildren(key).get(key);
  }

  @Override
  public List<Map<String, Object>> rangeGet(Integer start, Integer end) {
    return getChildren(start).rangeGet(start, end);
  }

  @Override
  public void set(Integer key, Map<String, Object> value) {
    this.getChildren(key).set(key, value);
  }

  @Override
  public boolean isOverFlow() {
    return this.keys.size() >= tree.getLeafNonNum();
  }

  @Override
  public Node spit() {
    // 分裂右子树操作
    NonLeafNode rightNode = new NonLeafNode(tree);
    // 设置新的偏移量信息
    rightNode.setPosition(tree.countAndSetNext());

    List<Integer> rightKeys = this.keys.subList(midNonLeafNum, this.keys.size());
    List<Long> rightAddressChildren =
        this.getChildrenAddress().subList(midNonLeafNum + 1, this.childrenAddress.size());

    rightNode.keys.addAll(rightKeys);
    rightNode.keys.remove(0);
    rightNode.childrenAddress.addAll(rightAddressChildren);

    rightKeys.clear();
    rightAddressChildren.clear();

    // 在发生分裂时,将分裂的节点进行保存操作
    tree.getChunkStream().writeChunk(rightNode, rightNode.getPosition());

    return rightNode;
  }

  @Override
  public Integer getFirstKey() {

    // 获取当前子节点的首个值信息
    long childrenPosition = this.getChildrenAddress().get(0);

    Node childrenNode = tree.getChunkStream().readerChunk(childrenPosition);

    return childrenNode.getFirstKey();
  }

  /**
   * 获取key
   *
   * @return
   */
  public List<Integer> getKeys() {
    return keys;
  }

  @Override
  public Long getPosition() {
    return position;
  }

  public List<Long> getChildrenAddress() {
    return childrenAddress;
  }

  @Override
  public NodeTypeEnum getNodeType() {
    return nodeType;
  }

  @Override
  public void setPosition(Long position) {
    this.position = position;
  }
}
