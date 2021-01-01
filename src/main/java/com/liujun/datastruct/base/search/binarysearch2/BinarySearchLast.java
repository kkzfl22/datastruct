package com.liujun.datastruct.base.search.binarysearch2;

/**
 * 最简单的二分查找算法
 *
 * <p>在一个有充的数组内进行二分查找
 *
 * @author liujun
 * @version 0.0.1
 */
public class BinarySearchLast {

  /**
   * 在一个重复的数组内，查找最后一个值等于给定值的元素
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

      // 1,如果区间中间件等于查找到的值
      if (data[mid] == value) {
        // 如果已经为最后值，说明为最后一个，
        // 如果后一个元素与给定值不同，也说明为最后一个
        if (mid == high || data[mid + 1] != value) {
          return mid;
        }
        // 如果非最后一个，则在右区间继续查找
        else {
          low = mid + 1;
        }
      }
      // 如果区间中间值大于给定值，则说明需要继续在左区间查找
      else if (data[mid] > value) {
        high = mid - 1;
      }
      // 区间中间值小于给定值，则需要继续右区间中查找
      else {
        low = mid + 1;
      }
    }

    return -1;
  }
}
