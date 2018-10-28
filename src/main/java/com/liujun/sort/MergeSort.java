package com.liujun.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * <p>归并排序的递推公式为marge_sort(q...r)=marge(margesort(q...q),margesort(q+1,n)) 终止条件为q>=r
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class MergeSort {

  public void mergeSort(int[] data) {
    this.mergeSortMethod(data, 0, data.length - 1);
  }

  private void mergeSortMethod(int[] data, int start, int end) {
    if (start >= end) {
      return;
    }

    int mid = (start + end) / 2;

    mergeSortMethod(data, start, mid);
    mergeSortMethod(data, mid + 1, end);

    merge(data, start, mid, end);
  }

  /**
   * 进行数据合并操作
   *
   * @param data 数据
   * @param start 开始索引
   * @param mid 中间的索引
   * @param end 结束位置
   */
  private void merge(int[] data, int start, int mid, int end) {
    int[] tmpData = new int[end - start + 1];
    int tmpIndex = 0;

    // 进行数据合并操作
    int leftIndex = start;
    int rightIndex = mid + 1;

    while (leftIndex <= mid && rightIndex <= end) {
      if (data[leftIndex] < data[rightIndex]) {
        tmpData[tmpIndex++] = data[leftIndex++];
      } else {
        tmpData[tmpIndex++] = data[rightIndex++];
      }
    }

    // 将未结束的数据拷贝至申请数据的尾部
    int copyStartIndex = leftIndex;
    int copyEndIndex = mid;

    if (rightIndex <= end) {
      copyStartIndex = rightIndex;
      copyEndIndex = end;
    }

    while (copyStartIndex <= copyEndIndex) {
      tmpData[tmpIndex++] = data[copyStartIndex++];
    }

    // 将数据重新规换回数组中
    for (int i = 0; i <= end - start; i++) {
      data[start + i] = tmpData[i];
    }
  }
}
