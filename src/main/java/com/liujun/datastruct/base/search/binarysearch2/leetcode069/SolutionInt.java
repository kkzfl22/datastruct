package com.liujun.datastruct.base.search.binarysearch2.leetcode069;

/**
 * @author liujun
 * @version 0.0.1
 */
public class SolutionInt {

  /**
   * 求平方根的函数，
   *
   * @param x 数值
   * @return 平台根的结果
   */
  public int mySqrt(int x) {

    if (x <= 1) {
      return x;
    }

    int tempData = x;
    int low = 1, mid, high = x, contMs, result = x;

    while (low <= high) {
      mid = low + (high - low) / 2;

      contMs = tempData / mid;
      // 如果找到平方根，则返回
      if (mid == contMs) {
        return mid;
      }
      // 如果当前的数比平方根要大，则向左继续进行
      else if (mid > contMs) {
        high = mid - 1;
      }
      // 如果当前的数比平方根要小，则向右继续进行
      else {
        low = mid + 1;
        result = mid;
      }
    }

    return result;
  }
}
