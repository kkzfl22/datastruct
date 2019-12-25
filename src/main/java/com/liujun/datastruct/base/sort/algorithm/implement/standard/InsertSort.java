package com.liujun.datastruct.base.sort.algorithm.implement.standard;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.util.Arrays;

/**
 * @author
 * @version 0.0.1
 * @date
 */
public class InsertSort<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {
    // 插入排序，a 表示数组，n表示数组大小

    if (data.length <= 1) return;

    int dataLen = data.length;

    for (int i = 1; i < dataLen; ++i) {
      T value = data[i];
      int j = i - 1;
      // 查找插入的位置,并进行数据移动
      for (; j >= 0; --j) {
        if (((Comparable) data[j]).compareTo(value) > 0) {
          // 数据移动
          data[j + 1] = data[j];
        } else {
          break;
        }
      }
      // 插入数据
      data[j + 1] = value;
      // 打印相关的值
      System.out.println("当前第" + i + "次的值为:" + Arrays.toString(data));
    }
  }
}
