package com.liujun.datastruct.base.search.binarysearch2;

/**
 * 最简单的二分查找算法
 *
 * <p>在一个有充的数组内进行二分查找
 *
 * @author liujun
 * @version 0.0.1
 */
public class BinarySearchEqualOrLessThan {

  /**
   * 查找最后一个小于等于给定值的元素
   *
   * @param data
   * @param value
   * @return
   */
  public int search(int[] data, int value) {

    if (null == data || data.length == 0) {
      return -1;
    }

    int low = 0, mid, high = data.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 如果当前区间中间值小于等于给定值
      if (data[mid] <= value) {
        // 如果当前为最后一个，则直接返回
        // 或者当前区间中间值的后一个元素大于给定元素，也返回
        if (mid == high || data[mid + 1] > value) {
          return mid;
        }
        // 否则继续在右区间继续查找
        else {
          low = mid + 1;
        }
      }
      // 如果当前区间中间值大于给定值，则在左区间中查找
      else {
        high = mid - 1;
      }
    }

    return -1;
  }
}
