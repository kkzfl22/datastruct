package com.liujun.datastruct.hash.linked;

/**
 * 使用链表法来编写散列表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/09
 */
public class MyLinkedHash {

  class Data {
    /** 用做存储散列的key信息 */
    private String key;

    /** 用作存储散列的value信息 */
    private String value;

    /** 下一个数据指针 */
    private Data next;
  }

  /** 数据存储区 */
  private final Data[] data;

  /** 容量 */
  private final int capacity;

  public MyLinkedHash(int capacity) {
    this.capacity = capacity;
    this.data = new Data[capacity];
  }

  public void push(String key, String value) {
    // 计算出桶的位置
    int index = hash(key);

    if (data[index] == null) {
      data[index] = new Data();
    }
    // 1,遍历链表查询数据是否已经存在
    Data tmpNode = data[index];

    Data findNode = null;

    while (tmpNode.next != null) {
      if (key.equals(tmpNode.key)) {
        findNode = tmpNode;
      }
      tmpNode = tmpNode.next;
    }

    Data currData = new Data();
    currData.key = key;
    currData.value = value;

    // 未找节点，
    if (null == findNode) {
      // 为空说明为头节点
      if (null == tmpNode) {
        data[index].next = currData;
      }
      // 找到链表中的最后一个节点，加入
      else {
        tmpNode.next = currData;
      }
    }
    // 如果能够查询到节点，说明已经存在，直接替换对象即可
    else {
      findNode.value = value;
    }
  }

  public void printNodeTree() {
    Data getData;
    for (int i = 0; i < capacity; i++) {
      getData = data[i];
      while (null != getData && getData.next != null) {
        getData = getData.next;
        System.out.print(getData.key + ":" + getData.value + ",");
      }
      System.out.println();
    }
  }

  /**
   * 获取值信息
   *
   * @param key 查询的key信息
   * @return 查询的值
   */
  public String get(String key) {
    // 1,计算当前的hash索引值
    int index = this.hash(key);

    Data getData = data[index];

    // 取出整条链表进行遍历
    while (getData != null) {
      if (key.equals(getData.key)) {
        break;
      }
      getData = getData.next;
    }

    if (null != getData) {
      return getData.value;
    }
    return null;
  }

  private int hash(String key) {
    return key.hashCode() % (capacity - 1);
  }

  int hash(Object key) {
    int h = key.hashCode();
    // capicity 表示散列表的大小
    return (h ^ (h >>> 16)) & (capacity - 1);
  }
}
