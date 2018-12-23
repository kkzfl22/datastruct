package com.liujun.datastruct.sort;

/**
 * 插入排序
 *
 * 最好时时间复杂度，如果一个数据是有序的，只需要一次的遍历，无需移动，故时间复杂度为O(n)
 *
 * 最坏时间复杂度，如果一个数据是倒序的，需要进行全部的数据移动，帮时间时间复杂度为O(n的平方)
 *
 * 平时时间复试度为，在一个数组中插入数据的时间复杂度为O(n)，插入排序，相关于循环N次进行插入排序，时间复杂度为O(n的平方)
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class InsertSort {

  public void insertSort(int[] data) {
    if (data.length < 1) {
      return;
    }

    int length = data.length;

    // 遍历所有未排序的数据
    for (int i = 1; i < length; ++i) {
      // 第一个元素为已排序数据中的首个
      int value = data[i];
      int j = i - 1;
      // 针对已经排序的数组，空出当前插入位置，优先进行移动
      for (; j >= 0; j--) {
        if (data[j] > value) {
          data[j + 1] = data[j];
        } else {
          break;
        }
      }

      data[j + 1] = value;
    }
  }

  public void insertSortMy(int[] data) {
    if (data.length < 1) {
      return;
    }
    int len = data.length;

    for (int i = 1; i < len; i++) {
      int tmpVal = data[i];
      int j = i - 1;

      for (; j >= 0; j--) {
        if (data[j] > tmpVal) {
          data[j + 1] = data[j];
        } else {
          break;
        }
      }

      data[j + 1] = tmpVal;
    }
  }
}
