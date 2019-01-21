package com.liujun.datastruct.base.search.binarysearch;

/**
 * 二分查找，查找最后一个小于给值的元素
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/04
 */
public class BinarySearchLess {

  /**
   * 使用二分查找最后一个小于等于给定值的元素
   *
   * @param data 数据
   * @param lessValue 小于等于的值
   * @return 返回下标
   */
  public int binarySearch(int[] data, int lessValue) {
    int mid;
    int start = 0;
    int end = data.length - 1;

    while (start <= end) {
      mid = start + (end - start) / 2;
      // 如果当前中间值已经小于等于当前终比较值，则需要分情况
      if (data[mid] <= lessValue) {
        // 1,检查当前是否已经满足第一个小于等于给值值的条件
        // 中间值为第一个，说明已经是第一个了，无需再向前，
        // 当前中间值的后一个值大于比较值，说明前一个满足小于等于
        if (mid == 0 || data[mid + 1] > lessValue) {
          return mid;
        }
        // 其他条件，则需要继续继续在
        else {
          start = mid + 1;
        }
      }
      // 如果发现，当前中间值是大于当前比较的值，说明需要在左区间继续查找
      else {
        end = mid - 1;
      }
    }
    return -1;
  }
}
