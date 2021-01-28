package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据对比结果
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompareRsp {

  /** 增加数据内容 */
  private List<String> addList;

  /** 修改的之前的数据内容 */
  private List<String> updateBeforeList;

  /** 修改的之后的数据的内容 */
  private List<String> updateAfterList;

  /** 删除数据内容 */
  private List<String> deleteList;

  /** 初始化原始对比结果集 */
  public void initCompareSrc() {
    this.addList = new ArrayList<>();
    this.updateAfterList = new ArrayList<>();
  }

  /** 初始化目标对比结果 */
  public void initCompareTarget() {
    this.deleteList = new ArrayList<>();
    this.updateBeforeList = new ArrayList<>();
  }

  public List<String> getAddList() {
    return addList;
  }

  public List<String> getUpdateBeforeList() {
    return updateBeforeList;
  }

  public List<String> getUpdateAfterList() {
    return updateAfterList;
  }

  public List<String> getDeleteList() {
    return deleteList;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DataCompareRsp{");
    sb.append("addList=").append(addList);
    sb.append(", updateBeforeList=").append(updateBeforeList);
    sb.append(", updateAfterList=").append(updateAfterList);
    sb.append(", deleteList=").append(deleteList);
    sb.append('}');
    return sb.toString();
  }
}
