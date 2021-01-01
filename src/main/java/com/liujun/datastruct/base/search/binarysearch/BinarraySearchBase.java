package com.liujun.datastruct.base.search.binarysearch;

/**
 * 最基础的二分查找
 *
 * <p>在有个有序的数据中做二分查找
 *
 * @author liujun
 * @version 0.0.1
 */
public class BinarraySearchBase {

  /**
   * 二分查找
   *
   * @param data
   * @param value
   */
  public int arraySearch(int[] data, int value) {
    // 声明头指针与尾指针
    int first = 0;
    int less = data.length - 1;

    int mid = 0;
    // 遍历的条件就是头指针大于等于尾指针，说明已经重叠
    while (first <= less) {
      // 中间元素位置
      mid = (first + less) / 2;
      // (first + less)的写法存在问题，当取值很大时，可能会溢出
      // mid = (first + (less - first) / 2);
      // 最极致的写法使用位运算
      // mid = first + (less - first) >> 1;

      if (data[mid] == value) {
        return mid;
      }
      // 如果值小于中间元素，说明在左区间
      // 头索引不变，尾指针指向中间
      else if (value < data[mid]) {
        less = mid - 1;
      }
      // 如果值大于中间元素，说明在右区间
      // 尾索引不变，头指针指向中间
      else if (value > data[mid]) {
        first = mid + 1;
      }
    }
    // 经过查找没有找到，则返回-1
    return -1;
  }
}
