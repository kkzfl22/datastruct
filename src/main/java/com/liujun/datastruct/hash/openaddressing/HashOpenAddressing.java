package com.liujun.datastruct.hash.openaddressing;

/**
 * 采用开放地址法来编写一个散列表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/08
 */
public class HashOpenAddressing {

  class Data {
    /** 用做存储散列的key信息 */
    private String key;

    /** 用作存储散列的value信息 */
    private String value;
  }

  private final Data[] data;

  private final int capacity;

  public HashOpenAddressing(int capacity) {
    this.capacity = capacity;
    this.data = new Data[capacity];
  }

  /**
   * 存储的信息
   *
   * @param key
   * @param value
   * @return
   */
  public boolean put(String key, String value) {
    boolean putRsp = false;

    int index = hash(key);
    // 检查此位置是否已经存值
    if (data[index] == null || data[index].key == key) {
      data[index] = new Data();
      data[index].key = key;
      data[index].value = value;
      putRsp = true;
    }
    // 如果当前索引已经存在值，则向后查找
    else {
      for (int i = index + 1; i < capacity; i++) {
        if (data[i] == null) {
          data[i] = new Data();
          data[i].key = key;
          data[i].value = value;
          putRsp = true;
          break;
        }
      }
    }

    return putRsp;
  }

  /**
   * 获取一个数据信息
   *
   * @param key 获取的key信息
   * @return
   */
  public String get(String key) {
    String resultVal = null;

    int index = hash(key);
    if (data[index].key.equals(key)) {
      resultVal = data[index].value;
    } else {
      for (int i = index + 1; i < capacity; i++) {
        if (data[i].key.equals(key)) {
          resultVal = data[i].value;

          break;
        }
      }
    }

    return resultVal;
  }

  private int hash(String key) {

    return key.hashCode() % capacity;
  }
}
