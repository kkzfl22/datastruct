package com.liujun.datastruct.base.search.binarysearch;

/**
 * 查找第一个大于等于给定元素的值
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class BinarySearchGreater {

  public int binarySearch(int[] data, int value) {
    int mid;
    int start = 0;
    int end = data.length - 1;

    while (start <= end) {
      mid = start + (end - start) / 2;

      // 如果当前中间件，大于给定值，说明需要在左区间继续查找
      if (data[mid] >= value ) {
        if (mid == 0 || (data[mid - 1] < value)) {
          return mid;
        } else {
          end = mid - 1;
        }
      }
      // 否则就需要在右区间进行查找
      else {
        start = mid + 1;
      }
    }

    return -1;
  }
}
