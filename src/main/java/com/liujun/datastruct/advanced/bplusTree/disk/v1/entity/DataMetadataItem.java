package com.liujun.datastruct.advanced.bplusTree.disk.v1.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 存储数据的元数据信息
 *
 * @author liujun
 * @since 2022/11/21
 */
@Getter
@Setter
@ToString
public class DataMetadataItem {

  /** 索引号 */
  private Integer index;

  /** 名称信息 */
  private String name;

  /** 描述信息 */
  private String comment;

  /** 数据类型信息 */
  private Integer dataType;

  /** 数据长度信息 */
  private Integer length;

  /** 关联使用的AVA的数据类型 */
  private Class javaType;
}
