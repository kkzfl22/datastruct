package com.liujun.datastruct.base.search.binarysearch2;

/**
 * 最简单的二分查找算法
 *
 * <p>在一个有充的数组内进行二分查找
 *
 * @author liujun
 * @version 0.0.1
 */
public class BinarySearchEqualOrGreaterThan {

  /**
   * 查找第一个大于等于组定值的元素
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

      // 当前区间中间值如果大于等于给定值
      if (data[mid] >= value) {
        // 如果为首元素或者前一个元素小于给定元素，则结束
        if (mid == 0 || data[mid - 1] < value) {
          return mid;
        }
        // 否则继续向左区间查找
        else {
          high = mid - 1;
        }
      }
      // 如果当前区间中间值小于给定值，则在右区间查找
      else {
        low = mid + 1;
      }
    }

    return -1;
  }
}
