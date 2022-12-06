package com.liujun.datastruct.advanced.bplusTree.disk.v1.treestruct;

import com.liujun.datastruct.advanced.bplusTree.disk.v1.constant.NodeTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 节点的抽象对象
 *
 * @author liujun
 * @since 2022/11/24
 */
public abstract class Node {

  /**
   * 数据的插入
   *
   * @param key
   * @param value
   */
  public abstract void add(Integer key, Map<String, Object> value);

  /**
   * 数据的获取操作
   *
   * @param key
   * @return
   */
  public abstract Map<String, Object> get(Integer key);

  /**
   * 范围获取
   *
   * @param start 开始
   * @param end 结束
   * @return 查询的集合
   */
  public abstract List<Map<String, Object>> rangeGet(Integer start, Integer end);

  /**
   * 数据的修改操作
   *
   * @param key 主键
   * @param value 最亲折值
   */
  public abstract void set(Integer key, Map<String, Object> value);

  /**
   * 是否到达分裂条件
   *
   * @return
   */
  public abstract boolean isOverFlow();

  /**
   * 分裂操作
   *
   * @return
   */
  public abstract Node spit();

  /**
   * 获取首个节点的Key
   *
   * @return
   */
  public abstract Integer getFirstKey();

  /**
   * 获取节点的开始位置
   *
   * @return
   */
  public abstract Long getPosition();

  /**
   * 设置节点的开始位置
   *
   * @return
   */
  public abstract void setPosition(Long position);

  /**
   * 获取节点的类型信息
   *
   * @return
   */
  public abstract NodeTypeEnum getNodeType();
}
