package com.liujun.datastruct.advanced.bplusTree.disk.v1.treestruct;

import com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo.BinaryFunction;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.constant.NodeTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 叶子节点信息
 *
 * @author liujun
 * @since 2022/11/24
 */
public class LeafNode extends Node {

  /** 不占用空间的地址信息 */
  public static final long NONE_ADDRESS = -1;

  /** 节点类型信息 */
  private NodeTypeEnum nodeType;

  /** 叶子节点的中位数节点 */
  private final int leafMidNum;

  /** 索引的数据 */
  private final List<Integer> keys;

  /** 记录下当前节点开始的位置 */
  private Long position;

  /** 数据存储的值 */
  private final List<Map<String, Object>> value;

  /** 下一个存储数据的地址,默认不指向数据 */
  private long nextAddress = NONE_ADDRESS;

  /** 上一个存储数据的地址，默认不指向数据 */
  private long prevAddress = NONE_ADDRESS;

  /** 根节点信息 */
  private BPlusTree tree;

  public LeafNode(BPlusTree tree) {
    this.leafMidNum = tree.getLeafNum() / 2;
    this.keys = new ArrayList<>(tree.getLeafNum());
    this.value = new ArrayList<>(tree.getLeafNum());
    this.tree = tree;
    // 标识当前为叶子节点
    this.nodeType = NodeTypeEnum.LEAF;
  }

  @Override
  public void add(Integer key, Map<String, Object> value) {

    // 1,查找当前数据插入的位置
    int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);

    int insertIndex;
    if (searchIndex >= 0) {
      insertIndex = searchIndex + 1;
    } else {
      insertIndex = -searchIndex - 1;
    }

    this.keys.add(insertIndex, key);
    this.value.add(insertIndex, value);

    // 进行数据落盘操作
    tree.getChunkStream().writeChunk(this, this.position);

    if (tree.getRoot().isOverFlow()) {
      Node leafRightNode = this.spit();

      // 将节点进行保存
      tree.getChunkStream().writeChunk(this, this.position);

      // 写入新的根节点至磁盘中
      NonLeafNode newRoot = new NonLeafNode(tree);
      // 设置存储的位置
      newRoot.setPosition(tree.countAndSetNext());
      // 在key中加入值
      newRoot.getKeys().add(leafRightNode.getFirstKey());
      // 计算当前的地址
      newRoot.getChildrenAddress().add(this.position);
      newRoot.getChildrenAddress().add(leafRightNode.getPosition());

      // root一般在内存中，帮不保存操作
      tree.setRoot(newRoot);
    }
  }

  @Override
  public Map<String, Object> get(Integer key) {

    int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);

    if (searchIndex >= 0) {
      return value.get(searchIndex);
    }

    return null;
  }

  @Override
  public List<Map<String, Object>> rangeGet(Integer start, Integer end) {

    // 1,找到首个节点
    int searchIndex = BinaryFunction.indexedBinarySearch(keys, start);

    if (searchIndex < 0) {
      searchIndex = -searchIndex - 1;
    }

    List<Map<String, Object>> resultList = new ArrayList<>();
    boolean breakFlag = false;

    for (int i = searchIndex; i < keys.size(); i++) {
      if (keys.get(i).compareTo(end) < 0) {
        resultList.add(value.get(i));
      } else {
        breakFlag = true;
      }
    }

    if (breakFlag) {
      return resultList;
    }

    LeafNode nextNode = (LeafNode) tree.getChunkStream().readerChunk(this.nextAddress);
    breakFlag = false;
    while (nextNode != null) {
      for (int i = 0; i < nextNode.keys.size(); i++) {
        if (nextNode.keys.get(i).compareTo(end) < 0) {
          resultList.add(nextNode.value.get(i));
        } else {
          breakFlag = true;
          break;
        }
      }
      if (nextNode.getNextAddress() == NONE_ADDRESS) {
        break;
      }

      nextNode = (LeafNode) tree.getChunkStream().readerChunk(nextNode.nextAddress);
      if (breakFlag) {
        break;
      }
    }

    return resultList;
  }

  @Override
  public void set(Integer key, Map<String, Object> value) {
    int searchIndex = BinaryFunction.indexedBinarySearch(keys, key);

    if (searchIndex < 0) {
      return;
    }

    this.value.set(searchIndex, value);
  }

  @Override
  public boolean isOverFlow() {
    return this.keys.size() >= tree.getLeafNum();
  }

  @Override
  public Node spit() {
    LeafNode rightNode = new LeafNode(tree);
    // 设置新的偏移位置
    rightNode.setPosition(tree.countAndSetNext());

    List<Integer> rightKeys = this.keys.subList(leafMidNum, this.keys.size());
    List<Map<String, Object>> rightValues = this.value.subList(leafMidNum, this.value.size());

    rightNode.keys.addAll(rightKeys);
    rightNode.value.addAll(rightValues);

    // 将原来节点中的数据进行清除操作
    rightKeys.clear();
    rightValues.clear();

    // 使用最后的地址加上一个内存页的大小，即为下一个开始的位置
    rightNode.prevAddress = this.position;
    rightNode.nextAddress = this.nextAddress;
    this.nextAddress = rightNode.position;

    // 将分裂出去的新节点保存
    tree.getChunkStream().writeChunk(rightNode, rightNode.getPosition());

    return rightNode;
  }

  @Override
  public Integer getFirstKey() {
    return this.keys.get(0);
  }

  public List<Map<String, Object>> getValue() {
    return value;
  }

  @Override
  public Long getPosition() {
    return position;
  }

  public List<Integer> getKeys() {
    return keys;
  }

  public void setNodeType(NodeTypeEnum nodeType) {
    this.nodeType = nodeType;
  }

  public long getNextAddress() {
    return nextAddress;
  }

  public void setNextAddress(long nextAddress) {
    this.nextAddress = nextAddress;
  }

  public long getPrevAddress() {
    return prevAddress;
  }

  public void setPrevAddress(long prevAddress) {
    this.prevAddress = prevAddress;
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
