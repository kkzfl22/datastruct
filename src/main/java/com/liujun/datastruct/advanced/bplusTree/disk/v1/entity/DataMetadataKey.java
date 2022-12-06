package com.liujun.datastruct.advanced.bplusTree.disk.v1.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 存储数据的元数据主键信息
 *
 * @author liujun
 * @since 2022/11/21
 */
@Getter
@Setter
@ToString
public class DataMetadataKey {

  /** 数据类型的标识 */
  private Integer dataType;

  /** 数据长度信息 */
  private Integer length;

  /** 名称信息 */
  private String name;

  /** 描述信息 */
  private String comment;

  /** 关联使用的AVA的数据类型，必须实现Comparable接口 */
  private Class javaType;
}
