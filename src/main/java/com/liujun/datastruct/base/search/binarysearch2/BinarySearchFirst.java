package com.liujun.datastruct.base.search.binarysearch2;

/**
 * 最简单的二分查找算法
 *
 * <p>在一个有充的数组内进行二分查找
 *
 * @author liujun
 * @version 0.0.1
 */
public class BinarySearchFirst {

  /**
   * 在一个重复的数组内，查找值等于给定值的元素，重复需要返回第一个
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
      // 当查找到当前元素为给定元素
      if (data[mid] == value) {
        // 由于存在重复，需在检查是否为第一次出现
        //为0，则肯定为首次出现，前一个元素不与当前元素相同，也说明首次出现
        if (mid == 0 || data[mid - 1] != value) {
          return mid;
        }
        // 非第一次出现，继续向左搜索
        else {
          high = mid - 1;
        }
      }
      // 如果当前值中间值大于给定值，则在左区间查找
      else if (data[mid] > value) {
        high = mid - 1;
      }
      // 如果当前中间件小于给定值，则在右区间搜索
      else {
        low = mid + 1;
      }
    }

    return -1;
  }
}
