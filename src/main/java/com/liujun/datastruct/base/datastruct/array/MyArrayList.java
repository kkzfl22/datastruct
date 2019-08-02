package com.liujun.datastruct.base.datastruct.array;

/**
 * 实现一个支持动态扩容的数组
 *
 * 并且能够增删改查
 *
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/08
 */
public class MyArrayList {

  /** 初始化数组的大小操作 */
  public static final int INIT_SIZE = 10;

  private Object[] objects;

  /** 当前集合的最大大小 */
  private int currMaxSize;

  /** 写入数据的索引号 */
  private int index;

  public MyArrayList(int init) {
    objects = new Object[init];
    this.currMaxSize = init;
  }

  public MyArrayList() {
    this(INIT_SIZE);
  }

  public void add(Object value) {
    // 1,判断是否扩容
    boolean fullFlag = this.isFull();

    if (fullFlag) {
      // 1,获取新的数组
      Object[] newArrays = this.newArrays();
      // 拷贝数据
      System.arraycopy(objects, 0, newArrays, 0, objects.length);
      // 设置新的属性信息
      setObjs(newArrays);
    }

    // 添加数据
    this.objects[index++] = value;
  }

  /** 设置新的实例对象 */
  private void setObjs(Object[] newArrays) {
    this.objects = newArrays;
    this.currMaxSize = newArrays.length;
  }

  private Object[] newArrays() {
    int newSize = currMaxSize * 2;

    Object[] tempObj = new Object[newSize];

    return tempObj;
  }

  private boolean isFull() {
    if (this.index + 1 >= objects.length) {
      return true;
    }
    return false;
  }

  public int size() {
    return index;
  }

  /**
   * 进行数据中的元素删除
   *
   * @param delIndex
   */
  public void delete(int delIndex) {
    // 1,将数据移除
    if (delIndex > index) {
      throw new IllegalArgumentException("index out of array");
    }
    this.objects[delIndex] = 0;

    // 移动索引
    this.moveData();

    // 进行索引的减1操作
    this.index = this.index - 1;
  }

  private void moveData() {
    int moveIndex = -1;
    for (int i = 0; i < index; i++) {
      if (this.objects[i] == null && this.objects[i + 1] != null) {
        this.objects[i] = this.objects[i + 1];
        moveIndex = i + 1;
        continue;
      }

      if (moveIndex != -1) {
        this.objects[moveIndex] = this.objects[i];
        moveIndex = i;
      }
    }
  }

  /**
   * 进行数据的设置操作
   *
   * @param index 索引
   * @param value 值
   */
  public void set(int index, Object value) {
    this.objects[index] = value;
  }

  /**
   * 进行数据获取操作
   *
   * @param index 索引
   */
  public Object get(int index) {
    return this.objects[index];
  }



}
