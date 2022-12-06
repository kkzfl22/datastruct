package com.liujun.datastruct.advanced.bplusTree.disk.v1.disk;

import com.liujun.datastruct.advanced.bplusTree.disk.v1.entity.DataMetadata;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.entity.DataMetadataItem;

import java.util.List;

/**
 * 节点计算操作
 *
 * @author liujun
 * @since 2022/11/23
 */
public class NodeCount {

  private static final int PAGE_SIZE = SystemInfo.getPageSize();

  /** 子节点中数据存储的大小 */
  private static final int CHILD_LENGTH = 8;

  /** 指针的大小用于标识位置信息 */
  private static final int POINT_LENGTH = 8;

  /** 数据的长度信息 */
  private static final int KEY_LENGTH = 4;

  /**
   * 计算当前一个chunk块中，可以存储多少个数据节点
   *
   * <p>推导出
   *
   * @param metadata
   * @return
   */
  public static int countLeafNum(DataMetadata metadata) {

    int keyLength = metadata.getMetadataKey().getLength();
    int valueLength = valueLength(metadata.getMetadataValue());

    // 目前添加一个字节表示当前节点的类型，是子节点还是叶子节点
    int count = PAGE_SIZE - 1;

    // 减去一个位置的指针信息
    count = count - POINT_LENGTH;

    // 每个块下个数据地址及上个数据的地址,需要先减去
    count = count - (POINT_LENGTH + POINT_LENGTH);

    // 减去长度信息
    count = count - KEY_LENGTH;

    return count / (keyLength + valueLength);
  }

  private static int valueLength(List<DataMetadataItem> metadataList) {
    int sumLength = 0;

    for (DataMetadataItem item : metadataList) {
      sumLength = sumLength + item.getLength();
    }
    return sumLength;
  }

  /**
   * 计算当前一个chunk块中，可以存储多少个树枝节点
   *
   * <p>PAGE_SIZE = (m-1)*4[keywords的大小]+m*8[childred大小]
   *
   * <p>由于page_size已知
   *
   * <p>可推导得到m = PAGE_SIZE/(4+8-1)
   *
   * @param metadata
   * @return
   */
  public static int countLeafNonNum(DataMetadata metadata) {
    int keyLength = metadata.getMetadataKey().getLength();

    // 目前添加一个字节表示当前节点的类型，是子节点还是叶子节点
    int count = PAGE_SIZE - 1;
    // 每个索引块， 包括一个地址的的信息，占用8位
    count = count - CHILD_LENGTH;

    // 还得再多减去一个值，因为在非叶子节点，值比key多一个
    count = count - CHILD_LENGTH;

    // 减去长度信息
    count = count - KEY_LENGTH;

    // 存储的数据在边界时，会存在超过1个的情况，需要先进行存储
    return count / (keyLength + CHILD_LENGTH);
  }
}
