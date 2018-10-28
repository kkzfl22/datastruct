package com.liujun.sort;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class QuickSort {

  public void quickSort(int[] data) {
    this.quickSortMethod(data, 0, data.length - 1);
  }

  private void quickSortMethod(int[] data, int start, int end) {
    if (start >= end) {
      return;
    }

    int point = this.pattern(data, start, end);
    this.quickSortMethod(data, start, point - 1);
    this.quickSortMethod(data, point + 1, end);
  }

  public int pattern(int[] data, int start, int end) {
    int point = data[end];

    int i = start;

    int tmpData = 0;

    for (int j = start; j < end; j++) {
      if (data[j] < point) {
        tmpData = data[j];
        data[j] = data[i];
        data[i] = tmpData;
        i++;
      }
    }

    data[end] = data[i];
    data[i] = point;

    return i;
  }
}
