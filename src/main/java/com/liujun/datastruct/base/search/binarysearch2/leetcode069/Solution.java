package com.liujun.datastruct.base.search.binarysearch2.leetcode069;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /**
   * 求平方根的函数，
   *
   * @param x 数值
   * @param s 精度
   * @return 平台根的结果
   */
  public double mySqrt(int x, double s) {

    if (x <= 1) {
      return Double.NaN;
    }

    double tempData = x;
    double low = 1, mid, high = x, contMs;

    while (low <= high) {
      mid = low + (high - low) / 2;

      contMs = tempData / mid;

      // 如果找到平方根，则返回,或者精度满足要求也返回
      if (mid == contMs || high - low <= s) {
        return mid;
      }
      // 如果当前的数比平方根要大，则向左继续进行
      else if (mid > contMs) {
        high = mid;
      }
      // 如果当前的数比平方根要小，则向右继续进行
      else {
        low = mid;
      }
    }

    return Double.MIN_VALUE;
  }
}
