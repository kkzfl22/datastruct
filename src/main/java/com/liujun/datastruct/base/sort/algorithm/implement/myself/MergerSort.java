package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 归并排序的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/24
 */
public class MergerSort<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {

    if (null == data || data.length <= 1) {
      return;
    }
    if (!(data[0] instanceof Comparable)) {
      throw new IllegalArgumentException("data not implement compator interface");
    }

    // 1,归并排序操作
    recurstion(data, 0, data.length - 1);
  }

  private void recurstion(T[] data, int start, int end) {
    // 递归的终止条件
    if (start >= end) {
      return;
    }

    // 1,找到中间的索引位置
    int midIndex = (start + end) / 2;
    // 进行左半边数据递归操作
    recurstion(data, start, midIndex);
    // 进行右半边数据递归操作
    recurstion(data, midIndex + 1, end);

    // 进行mergesort的合并操作
    merget(data, start, midIndex, midIndex + 1, end);
  }

  /**
   * 进行数据的合并操作
   *
   * @param data 原始数据
   * @param leftstart 左开始索引
   * @param leftEnd 左结束索引
   * @param rightStart 右边开始索引
   * @param end 右边结束索引
   */
  private void merget(T[] data, int leftstart, int leftEnd, int rightStart, int end) {

    int dataLength = end - leftstart + 1;

    // 声明一个目标的数组大小
    T[] target = (T[]) Array.newInstance(data[0].getClass(), dataLength);
    int tarIndex = 0;

    int leftIndex = leftstart;
    int rightIndex = rightStart;

    while (leftIndex <= leftEnd && rightIndex <= end) {
      // if i < j ? -1  == 0 > 1
      if (((Comparable) data[leftIndex]).compareTo(data[rightIndex]) > 0) {
        target[tarIndex++] = data[rightIndex++];
      } else {
        target[tarIndex++] = data[leftIndex++];
      }
    }

    // 将剩余的值拷贝到目标数组中
    if (leftIndex <= leftEnd) {
      for (int i = leftIndex; i <= leftEnd; i++) {
        target[tarIndex++] = data[i];
      }
    } else if (rightIndex <= end) {
      for (int i = rightIndex; i <= end; i++) {
        target[tarIndex++] = data[i];
      }
    }

    // 将数据拷贝回原数组中
    for (int i = leftstart; i <= end; i++) {
      data[i] = target[i - leftstart];
    }

    System.out.println(Arrays.toString(data));
  }
}
