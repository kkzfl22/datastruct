package com.liujun.datastruct.base.sort.algorithm;

/**
 * 数居排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/16
 */
public interface SortInf<T> {

  /**
   * 进行排序操作
   *
   * @param data 待排序的原始内容
   */
  void sort(T[] data);
}
