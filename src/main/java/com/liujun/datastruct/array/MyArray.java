package com.liujun.datastruct.array;

/**
 * 自己实现一个集合数组，进入插入，删除，按下标访问操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/18
 */
public class MyArray {

  /** 存储数组的对象 */
  private int[] data;

  /** 数组的容量 */
  private int capacity;

  /** 数组中保存元素的个数 */
  private int count;

  public MyArray(int capacity) {
    if (capacity < 1) {
      return;
    }

    data = new int[capacity];
    this.capacity = capacity;
    this.count = 0;
  }

  public void addvalue(int value) {
    data[count] = value;
    count++;
  }

  public void setvalue(int index, int value) {
    data[index] = value;
  }

  /**
   * 根据索引查找数据
   *
   * @param index 数组下标索引
   * @return 返回数组中的值
   */
  public int findIndex(int index) {
    if (index < 1 || index > capacity) {
      return -1;
    }

    return data[index];
  }

  /**
   * 在指定的索引位置插入值
   *
   * @param index
   * @param value
   */
  public void insert(int index, int value) {

    if (index < 0 || index > capacity) {
      return;
    }

    int[] dataArra = data;

    // 1,检查容量是否已经满,如果已满，则需扩容，拷贝索引前数组
    if (count == capacity) {
      this.capacity = capacity + 1;
      dataArra = new int[this.capacity];
    }

    for (int i = 0; i < index; i++) {
      dataArra[i] = data[i];
    }
    dataArra[index] = value;
    for (int i = index + 1; i < this.capacity; i++) {
      dataArra[i] = data[i - 1];
    }

    count++;

    data = dataArra;
  }

  /**
   * 删除数组中的元素
   *
   * @param index
   */
  public void delete(int index) {
    if (index < 0 || index > capacity) {
      return;
    }

    for (int i = index; i < capacity - 1; i++) {
      data[i] = data[i + 1];
    }

    data[capacity - 1] = 0;
  }

  public int[] getArray() {
    return data;
  }
}
