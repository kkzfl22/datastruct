package com.liujun.datastruct.datacompare.bigfilecompare.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 */
public class DataArrays<V> {

  private V[] data;

  public DataArrays(Class<V> data, int capacity) {
    this.data = (V[]) Array.newInstance(data, capacity);
  }

  public void set(int index, V value) {
    data[index] = value;
  }

  public V get(int index) {
    return data[index];
  }

  public List<V> outList() {
    List<V> result = new ArrayList<>();

    for (int i = 0; i < data.length; i++) {
      result.add(mergeMin());
    }

    return result;
  }

  public V mergeMin() {
    V dataTmp = null;
    int minLast = -1;

    for (int i = 0; i < data.length; i++) {

      // 确保数据不为空
      if (null == data[i]) {
        continue;
      }

      // 第一次找到不为空的
      if (null != data[i] && minLast == -1) {
        minLast = i;
        dataTmp = data[i];
        continue;
      }

      int compareRsp = ((Comparable) data[minLast]).compareTo(data[i]);
      // 如果第一个数小于第二个数，则取第一个数
      if (compareRsp == 1) {
        dataTmp = data[i];
        minLast = i;
      }
    }

    if (minLast != -1) {
      // 将最小后，
      data[minLast] = null;
    }
    return dataTmp;
  }
}
