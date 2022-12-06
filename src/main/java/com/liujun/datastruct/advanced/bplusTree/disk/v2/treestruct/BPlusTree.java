package com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct;

import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.ChunkStreamOperator;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.FileOperator;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.NodeCount;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.RootNodeOperator;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.SystemInfo;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadata;

import java.util.List;
import java.util.Map;

/**
 * B+树的算法实现
 *
 * <p>此针对可以在磁盘存储的数据实现
 *
 * <p>1. 实现一棵B+树
 *
 * @author liujun
 * @since 2022/11/7
 */
public class BPlusTree {

  /** 叶子节点的数量 */
  private final int leafNum;

  /** 非叶子节点的数量 */
  private final int leafNonNum;

  /** 根节点 */
  private Node root;

  /** 写入数据的最后的位置 */
  private long lastWritePosition;

  /** 元数据信息 */
  private DataMetadata metadata;

  /** 文件操作 */
  private FileOperator fileOperator;

  /** 进行块的操作 */
  private ChunkStreamOperator chunkStream;

  /** root节点的持久华操作 */
  private RootNodeOperator rootOperator;

  public BPlusTree(DataMetadata metadata, String path, String fileName) {
    // 保存metadata
    this.metadata = metadata;
    // 计算叶子节点
    this.leafNum = NodeCount.countLeafNum(metadata);
    // 非叶子节点的数量计算
    this.leafNonNum = NodeCount.countLeafNonNum(metadata);
    // 根节持久华操作
    this.rootOperator = new RootNodeOperator(path);
    // 文件对象
    this.fileOperator = new FileOperator(path, fileName);
    // 打开文件流对象
    this.fileOperator.open();
    // 块信息的操作
    this.chunkStream = new ChunkStreamOperator(this);

    // 检查根节点是否存在
    if (rootOperator.exists()) {
      // 加载当前的root节点信息
      this.rootOperator = new RootNodeOperator(path);
      root = this.rootOperator.readerRootNode();
    }
    // 如果当前块不存在，则进行创建操作
    else {
      // 最后写入数据的偏移量
      this.lastWritePosition = 0;
      this.root = new LeafNode(this);
      this.root.setPosition(0L);
      this.rootOperator.writeRootNode(this.root);
    }
  }

  /** 计算下一个位置 */
  private long countNextChunk() {
    return this.lastWritePosition + SystemInfo.getPageSize();
  }

  /**
   * 计算并设置下一个节点的位置
   *
   * @return
   */
  public long countAndSetNext() {
    long nextPosition = this.countNextChunk();
    this.lastWritePosition = nextPosition;
    return nextPosition;
  }

  /** 保存根节点操作 */
  public void saveRootNode() {
    this.rootOperator.writeRootNode(root);
  }

  public void add(Integer key, Map<String, Object> value) {
    root.add(key, value);
  }

  public Map<String, Object> get(Integer key) {
    return root.get(key);
  }

  public List<Map<String, Object>> rangeGet(Integer start, Integer end) {
    return root.rangeGet(start, end);
  }

  public void set(Integer key, Map<String, Object> value) {
    root.set(key, value);
  }

  public Node getRoot() {
    return root;
  }

  public void setRoot(Node root) {
    this.root = root;
  }

  public DataMetadata getMetadata() {
    return metadata;
  }

  public FileOperator getFileOperator() {
    return fileOperator;
  }

  public long getLastWritePosition() {
    return lastWritePosition;
  }

  public void setLastWritePosition(long lastWritePosition) {
    this.lastWritePosition = lastWritePosition;
  }

  public int getLeafNum() {
    return leafNum;
  }

  public int getLeafNonNum() {
    return leafNonNum;
  }

  public ChunkStreamOperator getChunkStream() {
    return chunkStream;
  }
}
