package com.liujun.datastruct.base.datastruct.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * 进行斐波那契数列的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class RecursionFibonacci {

  public int fibonacci(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 1;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
  }

  private static final int MAX_DEPT_NUM = 100;

  public int fibonacciSetDept(int n) {
    return fibonacciSetMax(n, 0);
  }

  public int fibonacciSetMax(int n, int dept) {
    dept++;
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 1;
    }

    if (dept > MAX_DEPT_NUM) {
      throw new IllegalArgumentException(" dept more " + MAX_DEPT_NUM);
    }

    return fibonacciSetMax(n - 1, dept) + fibonacciSetMax(n - 2, dept);
  }

  Map<Integer, Integer> cacheValue = new HashMap<>();

  public int fibonacciAddCache(int n) {

    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 1;
    }

    // 当数据已经存在，则直接返回缓存中的数据
    if (cacheValue.containsKey(n)) {
      return cacheValue.get(n);
    }

    // 进行计算然后将数据放入表到缓存的散列表中
    int countValue = fibonacciAddCache(n - 1) + fibonacciAddCache(n - 2);
    cacheValue.put(n, countValue);

    return countValue;
  }

  public int fibonacciLoop(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 1;
    }

    int s = 1;
    int t = 1;

    int v = 0;

    for (int i = 3; i <= n; i++) {
      v = s + t;
      s = t;
      t = v;
    }

    return v;
  }
}
