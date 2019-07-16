package com.liujun.datastruct.base.datastruct.array;

/**
 * • 实现一个支持动态扩容的数组
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/08
 */
public class MyArrayList {

  private Object[] objects;

  /** 当前集合的最大大小 */
  private int currMaxSize;

  /** 初始化数组的大小操作 */
  public static final int INIT_SIZE = 10;

  public MyArrayList(int init) {
    objects = new Object[init];
    this.currMaxSize = init;
  }

  public void add(Object value) {
    // 1,判断是否扩容
  }

  /**
   * 进行数据中的元素删除
   *
   * @param index
   */
  public void delete(int index) {
    //

  }

  /**
   * 进行数据的设置操作
   *
   * @param index 索引
   * @param value 值
   */
  public void set(int index, Object value) {}

  /**
   * 进行数据获取操作
   *
   * @param index 索引
   */
  public Object get(int index) {
    return null;
  }
}
