package com.liujun.datastruct.base.datastruct.recursion;

/**
 * 递归求阶乘
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class RecurstionFactorial {

  public int factorial(int n) {
    if (n == 1) {
      return 1;
    }
    return factorial(n - 1) * n;
  }

  public int factorialLoop(int n) {
    if (n == 1) {
      return 1;
    }

    int sum = 1;

    for (int i = 2; i <= n; i++) {
      sum = sum * i;
    }

    return sum;
  }
}
