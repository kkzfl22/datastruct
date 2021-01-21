package com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity;

import java.util.List;

/**
 * 数据对比
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompareValueInfo<V> {

  /** 当前请求的数据集合 */
  private List<V> srcList;

  /** 数据库结果集 */
  private List<V> targetList;

  public DataCompareValueInfo(List<V> srcList, List<V> targetList) {
    this.srcList = srcList;
    this.targetList = targetList;
  }

  public List<V> getSrcList() {
    return srcList;
  }

  public void setSrcList(List<V> srcList) {
    this.srcList = srcList;
  }

  public List<V> getTargetList() {
    return targetList;
  }

  public void setTargetList(List<V> targetList) {
    this.targetList = targetList;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DataCompareReq{");
    sb.append("requestList=").append(srcList);
    sb.append(", dataBaseList=").append(targetList);

    sb.append('}');
    return sb.toString();
  }
}
