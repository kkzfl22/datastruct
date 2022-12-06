package com.liujun.datastruct.advanced.bplusTree.disk.v1.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 一个文件中的信息
 *
 * @author liujun
 * @since 2022/11/21
 */
@Getter
@Setter
@ToString
public class DataChunkData<K extends Comparable<K>, V> {

  /** 数据开始的位置 */
  private long position;

  /** 元数据信息 */
  private DataMetadata metadata;

  /** 写入的主键信息 */
  private List<K> keys;

  /** 写入的值信息 */
  private List<V> value;
}
