package com.liujun.datastruct.search.binarysearch;

/**
 * 二分查找的代码实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class BinarySearch {

  private int findIndex = 0;

  /**
   * 对数组进行二分查找,查找值的索引下标
   *
   * @param data 原数组
   * @param value 查找的数据
   * @return 值的索引下标
   */
  public int search(int[] data, int value) {

    int mid = 0;
    int low = 0;
    int hight = data.length - 1;

    while (low <= hight) {
      // 得到中间位置的索引下标
      mid = low + (hight - low) / 2;
      findIndex++;

      // 如果中间值就等于给定的值，则返回
      if (data[mid] == value) {
        System.out.println("中间值查找次数:" + findIndex);
        findIndex = 0;
        return mid;
      }
      // 进行优化查找
      if (data[low] == value) {
        System.out.println("大于值查找次数:" + findIndex);
        findIndex = 0;
        return low;
      }
      if (data[hight] == value) {
        System.out.println("小于值查找次数:" + findIndex);
        findIndex = 0;
        return hight;
      }

      // 如果中间值大于给定的值，说值查找的值，是需要继续在左区间查找
      if (data[mid] > value) {
        hight = mid - 1;
      }
      // 如果中间值小于给定值，说明查找的值，是需要继续在右区间继续查找
      else if (data[mid] < value) {
        low = mid + 1;
      }
    }
    findIndex = 0;
    return -1;
  }

  /**
   * 进行二分查找，使用递归来实现
   *
   * @param data 数据
   * @param value 查找的值
   * @return 索引下标
   */
  public int binarySearchRecursion(int[] data, int value) {
    return this.searchRecursion(data, value, 0, data.length - 1);
  }

  /**
   * 使用递归进行二分查找
   *
   * @param data 数据
   * @param value 值
   * @param start 开始
   * @param end 结束
   * @return 索引下标
   */
  private int searchRecursion(int[] data, int value, int start, int end) {

    int mid = (start + end) / 2;
    if (data[mid] == value) {
      return mid;
    }
    // 检查是否为开始或者结束值
    if (data[start] == value) {
      return start;
    }
    if (data[end] == value) {
      return end;
    }

    // 如果当前中间值大于，查找的值，说明查找的区间在左区间
    if (data[mid] > value) {
      return searchRecursion(data, value, start, mid - 1);
    }
    // 如果当前中间值小于查找的值，说明查找的区间在右区间
    else if (data[mid] < value) {
      return searchRecursion(data, value, mid + 1, end);
    }

    return -1;
  }
}
