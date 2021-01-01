package com.liujun.datastruct.base.search.binarysearch2.leetcode050;

/**
 * 计算x的民次幂
 *
 * <p>-2147483648 绝对值还是-2147483648
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionExample {

  public double myPow(double x, int n) {

    if (n == 0 || x == 1) {
      return 1;
    }


    if(n == Integer.MIN_VALUE){
      return x == -1 || x == 1 ? 1 : 0;
    }

    if (n < 0) {
      return 1 / recursion(x, -n);
    }

    return recursion(x, n);
  }

  private double recursion(double x, int n) {
    if (n == 1) {
      return x;
    }

    if (n % 2 == 0) {
      // 当数为偶数量，直接两个集相乘即为结果
      double value = recursion(x, n / 2);
      return value * value;
    } else {
      double value = recursion(x, n / 2);
      return value * value * x;
    }
  }
}
