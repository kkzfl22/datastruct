package com.liujun.sort;

/**
 * 选择排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class SelectSort {

  public void selectSort(int[] data) {
    if (data.length < 1) {
      return;
    }

    // 选择排序，开始遍历
    for (int i = 0; i < data.length; i++) {
      // 找到数组中的最小元素
      int findIndex = i;
      for (int j = findIndex; j < data.length; j++) {
        if (data[findIndex] < data[j]) {
          findIndex = j;
        }
      }

      // 进行进行交换
      if (i != findIndex) {
        int tmpValue = data[i];
        data[i] = data[findIndex];
        data[findIndex] = tmpValue;
      }
    }
  }
}
