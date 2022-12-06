package com.liujun.datastruct.advanced.bplusTree.disk.v2.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 存储数据的元数据信息
 *
 * @author liujun
 * @since 2022/11/21
 */
@Getter
@Setter
@ToString
public class DataMetadata implements Serializable {

  /** 主键的信息 */
  private DataMetadataKey metadataKey;

  /** 元数据信息 */
  private List<DataMetadataItem> metadataValue;

  public int getKeyLength() {
    if (null == metadataKey) {
      return 0;
    }

    if (null == metadataKey.getLength()) {
      return 0;
    }

    return metadataKey.getLength();
  }

  /**
   * 计算值的长度
   *
   * @return
   */
  public int getLeafValueLength() {
    if (null == metadataValue || metadataValue.isEmpty()) {
      return 0;
    }

    int sumLength = 0;
    for (int i = 0; i < metadataValue.size(); i++) {
      sumLength += metadataValue.get(i).getLength();
    }

    return sumLength;
  }

  /**
   * 计算非叶子节点的长度
   *
   * @return
   */
  public int getNonLeafLength() {
    return 8;
  }
}
