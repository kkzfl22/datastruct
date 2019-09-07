package com.liujun.datastruct.base.datastruct.recursion;

/**
 * 递归求解当前在第代的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class RecursionGeneration {

  public int countGeneration(int n) {
    if (n == 1) {
      return 8;
    }

    return countGeneration(n - 1) + 1;
  }

  public int countGenerationLoop(int n) {
    int start = 8;

    if (n == 1) {
      return start;
    }

    int countGeneration = start;

    for (int i = 1; i < n; i++) {
      countGeneration = countGeneration + 1;
    }

    return countGeneration;
  }
}
