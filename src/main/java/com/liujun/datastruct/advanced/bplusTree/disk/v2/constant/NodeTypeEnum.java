package com.liujun.datastruct.advanced.bplusTree.disk.v2.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * 节点类型
 *
 * @author liujun
 * @since 2022/11/30
 */
@Getter
@ToString
public enum NodeTypeEnum {

  /** 当前为叶子节点 */
  LEAF((byte) 1),

  /** 非叶子节点 */
  NON_LEAF((byte) 2),
  ;

  private byte type;

  NodeTypeEnum(byte type) {
    this.type = type;
  }

  public static NodeTypeEnum getType(byte type) {
    for (NodeTypeEnum typeItem : values()) {
      if (typeItem.type == type) {
        return typeItem;
      }
    }

    throw new RuntimeException("type " + type + " is error");
  }
}
