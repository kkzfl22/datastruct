package com.liujun.datastruct.base.search.binarysearch2;

/**
 * 最简单的二分查找算法
 *
 * <p>在一个有充的数组内进行二分查找
 *
 * @author liujun
 * @version 0.0.1
 */
public class BinarySearch {

  /**
   * 最简单的二分查找情况，在无重复的元素中去查找给定值
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
      // 如果当前中间值等于组定值，则返回
      if (data[mid] == value) {
        return mid;
      }
      // 如果当前中间值大于组定值，说明在左区间查找
      else if (data[mid] > value) {
        high = mid - 1;
      }
      // 如果当前中间小于给定值，则说明在右区间查找
      else {
        low = mid + 1;
      }
    }

    return -1;
  }
}
