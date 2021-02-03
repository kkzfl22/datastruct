package com.liujun.datastruct.datacompare.bigfilecompare.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据对比结果
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompareOutRsp {

  /** 增加数据内容 */
  private List<String> addList;

  /** 修改的之前的数据内容 */
  private List<String> updateList;

  /** 删除数据内容 */
  private List<String> deleteList;

  /**
   * 数据操作
   *
   * @param dataSize
   */
  public DataCompareOutRsp(int dataSize) {
    this.addList = new ArrayList<>();
    this.updateList = new ArrayList<>();
    this.deleteList = new ArrayList<>();
  }

  public List<String> getAddList() {
    return addList;
  }

  public List<String> getUpdateList() {
    return updateList;
  }

  public List<String> getDeleteList() {
    return deleteList;
  }

  /** 数据清理 */
  public void clean() {
    this.addList.clear();
    this.updateList.clear();
    this.deleteList.clear();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DataCompareOutRsp{");
    sb.append("addList=").append(addList);
    sb.append(", updateList=").append(updateList);
    sb.append(", deleteList=").append(deleteList);
    sb.append('}');
    return sb.toString();
  }
}
