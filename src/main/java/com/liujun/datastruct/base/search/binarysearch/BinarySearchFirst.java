package com.liujun.datastruct.base.search.binarysearch;

/**
 * 使用二分查找第一个给定值的元素
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class BinarySearchFirst {

  /**
   * 使用二分查找第一个给定值的元素
   *
   * @param data 原始数据信息
   * @param value 值信息
   * @return 索引下标
   */
  public int binarySearch(int[] data, int value) {
    int mid;
    int left = 0;
    int right = data.length -1;

    while (left <= right) {
      mid = left + (right - left) / 2;

      if (data[mid] == value) {
        // 如果当前mid等于第一个元素，说明即为查找的第一个
        // 如果当前中间值的前一个值不等于当前查找的值，说明也非当前需要查找的值
        if (mid == 0 || data[mid - 1] != value) {
          return mid;
        } else {
          right = mid - 1;
        }

      }
      // 如果当前中间值，小于给定值,说明要向右区间继续查找
      else if (data[mid] < value) {
        left = mid + 1;
      }
      // 如果当前中间值，大于给定值，说明要向左区间继续查找
      else {
        right = mid - 1;
      }
    }

    return -1;
  }
}
