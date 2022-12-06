package com.liujun.datastruct.advanced.bplusTree.disk.v2.disk;

import com.liujun.datastruct.advanced.bplusTree.disk.v2.constant.NodeTypeEnum;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.convert.ValueTypeEnum;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadataItem;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct.BPlusTree;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct.LeafNode;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct.Node;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct.NonLeafNode;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * 叶子节点的数据流操作
 *
 * @author liujun
 * @since 2022/12/1
 */
public class ChunkStreamOperator {

  /** 构建一个直接内存区域，可以反复的读取，少一次内存拷贝操作，在使用完毕后，需要清空 */
  private ByteBuffer buffer = ByteBuffer.allocateDirect(SystemInfo.getPageSize());

  /** 树状态节点信息 */
  private BPlusTree treeNode;

  /** 节点的索引位置 */
  private int nodeTypeIndex = 8;

  public ChunkStreamOperator(BPlusTree treeNode) {
    this.treeNode = treeNode;
  }

  /**
   * 读取一个节点的数据信息
   *
   * @param position
   * @return
   */
  public Node readerChunk(long position) {

    buffer.clear();

    // 从文件中读取一个内存块的数据至缓冲区中
    treeNode.getFileOperator().readerChunk(position, buffer);
    // 将磁盘中的数据读取至缓冲区中
    Node dataNode = bufferToNode();
    // 读取完成后，也进行清理
    buffer.clear();

    return dataNode;
  }

  /**
   * 将缓冲区中的数据转换为节点对象
   *
   * @return
   */
  private Node bufferToNode() {

    buffer.flip();
    // 读取节点的类型
    NodeTypeEnum nodeType = NodeTypeEnum.getType(buffer.get(nodeTypeIndex));

    // 进行叶子节点构建操作
    if (NodeTypeEnum.LEAF.equals(nodeType)) {
      return readerLeafNode();
    } else if (NodeTypeEnum.NON_LEAF.equals(nodeType)) {
      return readerLeafNonByBuffer();
    }

    throw new IllegalArgumentException("node type is error");
  }

  /**
   * 叶子节点的读取方法
   *
   * @return 叶子节点对象信息
   */
  private Node readerLeafNode() {
    LeafNode node = new LeafNode(treeNode);

    // 获取当前数据的长度

    // 当前节点的开始位置
    node.setPosition(buffer.getLong());

    // 读取节点的类型
    node.setNodeType(NodeTypeEnum.getType(buffer.get()));

    // 读取下一个数据指向的地址
    node.setNextAddress(buffer.getLong());
    node.setPrevAddress(buffer.getLong());

    // 读取当前已经写入的数据个数
    int keyArrayLength = buffer.getInt();

    // 已经存在数据读取操作
    for (int i = 0; i < keyArrayLength; i++) {
      // 向B+树中添加当前读取到key
      ValueTypeEnum dataKeyType =
          ValueTypeEnum.getType(treeNode.getMetadata().getMetadataKey().getDataType());
      node.getKeys().add((Integer) dataKeyType.getBufferToValue().bufferToValue(buffer));

      Map<String, Object> valueMap =
          new HashMap<>(treeNode.getMetadata().getMetadataValue().size(), 1);
      for (int j = 0; j < treeNode.getMetadata().getMetadataValue().size(); j++) {
        DataMetadataItem metadataItem = treeNode.getMetadata().getMetadataValue().get(j);
        ValueTypeEnum dataType = ValueTypeEnum.getType(metadataItem.getDataType());
        valueMap.put(metadataItem.getName(), dataType.getBufferToValue().bufferToValue(buffer));
      }
      // 向B+树中添加当前读取到的value
      node.getValue().add(valueMap);
    }

    return node;
  }

  /**
   * 读取叶子节点的信息
   *
   * @return
   */
  public Node readerLeafNonByBuffer() {
    NonLeafNode node = new NonLeafNode(treeNode);

    // 开始的地址
    node.setPosition(buffer.getLong());

    // 读取当前节点的类型
    buffer.get();

    // 读取当前已经写入的数据个数
    int keyArrayLength = buffer.getInt();

    // 读取key
    for (int i = 0; i <= keyArrayLength; i++) {
      // 由于key比值多一个的问题，需要少读取一次key
      if (i < keyArrayLength) {
        ValueTypeEnum dataType =
            ValueTypeEnum.getType(treeNode.getMetadata().getMetadataKey().getDataType());
        // 读取当前节点索引信息
        node.getKeys().add((Integer) dataType.getBufferToValue().bufferToValue(buffer));
      }
      // 读取当前节点的指针信息
      node.getChildrenAddress().add(buffer.getLong());
    }

    return node;
  }

  /**
   * 向块中写入数据
   *
   * @param node
   * @param position
   */
  public void writeChunk(Node node, long position) {

    buffer.clear();

    // 将当我前节点的数据，写入至buffer中
    dataToBuffer(node);

    // 将数据写入至文件中
    // 将写入指针改为读取指针从0开始
    buffer.flip();
    // 从文件中读取一个内存块的数据至缓冲区中
    treeNode.getFileOperator().writeChunk(position, buffer);
    // 强制写入磁盘
    treeNode.getFileOperator().force();

    // 清空缓冲区
    buffer.clear();
  }

  /**
   * 将数据转换到buffer中
   *
   * @param node
   */
  private void dataToBuffer(Node node) {
    NodeTypeEnum nodeType = node.getNodeType();
    // 非叶子节点数据的写入操作
    if (NodeTypeEnum.NON_LEAF.equals(nodeType)) {
      nonLeafToBuffer(node);
      return;
    } else if (NodeTypeEnum.LEAF.equals(nodeType)) {
      leafNodeToBuffer(node);
      return;
    }

    throw new IllegalArgumentException("node type is error");
  }

  /** 非叶子节点的写入 */
  private void nonLeafToBuffer(Node node) {
    NonLeafNode nonLeafNode = (NonLeafNode) node;
    // 位置信息
    long skipPosition = node.getPosition();
    // 写入节点的类型
    skipPosition = skipPosition + 1;

    // 写入节点的个数
    buffer.putInt(nonLeafNode.getKeys().size());

    // 追加最后一个数据
    for (int i = nonLeafNode.getKeys().size() - 1; i <= nonLeafNode.getKeys().size(); i++) {
      // 将已知的数据写入块中
      if (i < nonLeafNode.getKeys().size()) {
        ValueTypeEnum dataType =
            ValueTypeEnum.getType(treeNode.getMetadata().getMetadataKey().getDataType());
        dataType.getValueToBuffer().valueToBuffer(nonLeafNode.getKeys().get(i), buffer);
      }

      // 将数据写入缓冲区中
      buffer.putLong(nonLeafNode.childrenAddress.get(i));
    }
  }

  /**
   * 叶子节点的数据写入操作
   *
   * @param node
   */
  private void leafNodeToBuffer(Node node) {

    LeafNode leafNode = (LeafNode) node;

    // 位置信息
    buffer.putLong(node.getPosition());

    // 当前为叶子节点类型
    buffer.put(node.getNodeType().getType());

    // 写入指针位置
    // 写入下一个数据的地址
    buffer.putLong(leafNode.getNextAddress());
    // 写入上一个数据的地址
    buffer.putLong(leafNode.getPrevAddress());

    // 写入节点的个数
    buffer.putInt(leafNode.getKeys().size());

    // 数据写入操作
    for (int i = 0; i < leafNode.getKeys().size(); i++) {
      // 写入key的信息
      ValueTypeEnum dataKeyType =
          ValueTypeEnum.getType(treeNode.getMetadata().getMetadataKey().getDataType());
      dataKeyType.getValueToBuffer().valueToBuffer(leafNode.getKeys().get(i), buffer);

      // 写入值的信息
      for (int j = 0; j < treeNode.getMetadata().getMetadataValue().size(); j++) {
        DataMetadataItem metadataItem = treeNode.getMetadata().getMetadataValue().get(j);
        ValueTypeEnum dataValueType = ValueTypeEnum.getType(metadataItem.getDataType());
        Object value = leafNode.getValue().get(i).get(metadataItem.getName());
        dataValueType.getValueToBuffer().valueToBuffer(value, buffer);
      }
    }
  }
}
