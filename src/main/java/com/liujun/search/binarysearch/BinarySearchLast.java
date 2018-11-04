package com.liujun.search.binarysearch;

/**
 * 使用二分查找最后一个值等于定值的元素
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class BinarySearchLast {

  /**
   * 使用二分查找最后一个值小于等于给定值的元素
   *
   * @param data 原始数据信息
   * @param value 值信息
   * @return 索引下标
   */
  public int binarySearch(int[] data, int value) {
    int mid = 0;
    int start = 0;
    int end = data.length - 1;

    while (start <= end) {
      mid = start + (end - start) / 2;

      // 首先找到相等的值
      if (data[mid] == value) {
        // 查找出后一个元素的方法，即为当前为最后一个元素，或者当前节点的后一个节点不等于给定值
        if (mid == end || data[mid + 1] != value) {
          return mid;
        }
        // 否则，就需要继续在右区间进行查找，因为要查找最后一个，按顺序肯定在右边
        else {
          start = mid + 1;
        }
      }

      // 如果当前中间件大于给定值，说明需要在左区间查找
      else if (data[mid] > value) {
        end = mid - 1;
      }
      // 如果当前中间件小于给定值，说明需要在右区间查找
      else {
        start = mid + 1;
      }
    }

    return -1;
  }
}
