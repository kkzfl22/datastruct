package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/22
 */
public class SelectSort<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {
    if (null == data || data.length <= 1) {
      return;
    }
    if (!(data[0] instanceof Comparable)) {
      throw new IllegalArgumentException("data not implement compator interface");
    }

    int datLen = data.length;
    int minIndex = 0;
    // 进行遍历排序操作
    for (int i = 0; i < datLen; i++) {
      // 1,在剩余的数据中找到最小的数据
      T dataItem = data[i];
      minIndex = i;
      for (int j = i + 1; j < datLen; j++) {
        // 找到剩余数据中最小的数据
        // if i < j ? -1
        if (((Comparable) dataItem).compareTo(data[j]) > 0) {
          minIndex = j;
          dataItem = data[j];
        }
      }

      // 如果数据与当前i一致，则不做交换
      if (i != minIndex) {
        T tmpData = data[i];
        data[i] = data[minIndex];
        data[minIndex] = tmpData;
      }

      System.out.println("当前前第:" + i + "次：" + Arrays.toString(data));
    }
  }
}
