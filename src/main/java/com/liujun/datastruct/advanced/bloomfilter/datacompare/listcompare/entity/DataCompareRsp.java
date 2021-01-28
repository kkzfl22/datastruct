package com.liujun.datastruct.advanced.bloomfilter.datacompare.listcompare.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liujun
 * @version 0.0.1
 */
public class DataCompareRsp<V> {

  /** 当前请求的数据集合 */
  private List<V> addList;

  /** 修改的数据 */
  private Map<String, UpdateObjectEntity<V>> updateMap;

  /** 删除的数据 */
  private List<V> removeList;

  public void init() {
    addList = new ArrayList(1024);
    updateMap = new HashMap<>(2048);
    removeList = new ArrayList(1024);
  }

  public List<V> getAddList() {
    return addList;
  }

  public List<V> getRemoveList() {
    return removeList;
  }

  public Map<String, UpdateObjectEntity<V>> getUpdateMap() {
    return updateMap;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DataCompareRsp{");
    sb.append("insertList=").append(addList);
    sb.append(", updateMap=").append(updateMap);
    sb.append(", deleteList=").append(removeList);
    sb.append('}');
    return sb.toString();
  }
}
