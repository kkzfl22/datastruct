package com.liujun.datastruct.base.search.binarysearch2.leetcode050;

/**
 * 计算x的民次幂
 *
 * <p>使用暴力解法，直接乘,这个算法的时间复杂度为n
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionBase {

  public double myPow(double x, int n) {

    if (x == 0) {
      return 1;
    }

    if (n == 1) {
      return x;
    }

    double result = 1;

    int runNum = Math.abs(n);

    for (int i = 0; i < runNum; i++) {
      result *= x;
    }

    result = n < 0 ? 1.0 / runNum : result;

    return result;
  }
}
