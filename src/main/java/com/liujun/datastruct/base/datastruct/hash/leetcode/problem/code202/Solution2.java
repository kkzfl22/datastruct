package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code202;

/**
 * 判断是否为快乐数
 *
 * <p>其实快乐数有一个已被证实的规律：
 *
 * <p>​ 不快乐数的数位平方和计算，最后都会进入 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4 的循环体。
 *
 * <p>所以该题可以用递归来解，基线条件为 n < =4，满足基线体条件时，如果 n=1 则原数为快乐数，否则不是。
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public boolean isHappy(int n) {

    int data = n;
    while (data != 1 && data != 4) {

      int sum = 0;
      while (data > 0) {
        // 平方的计算
        int count = (data % 10) * (data % 10);
        data = data / 10;
        sum = sum + count;
      }

      data = sum;
    }

    return data == 1;
  }
}
