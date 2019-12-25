package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

/**
 * 冒泡排序算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/16
 */
public class BubbleSort<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {

    if (data == null || data.length <= 1) {
      return;
    }

    if (!(data[0] instanceof Comparable)) {
      throw new IllegalArgumentException("data not implement compator interface");
    }

    for (int i = 0; i < data.length; i++) {
      // 进行冒泡操作，
      // 每一次冒泡至少让一个元素到达排序后所在的位置
      for (int j = 0; j < data.length; j++) {
        if (((Comparable) data[i]).compareTo(data[j]) < 0) {
          T item = data[i];
          data[i] = data[j];
          data[j] = item;
        }
      }
    }
  }
}
