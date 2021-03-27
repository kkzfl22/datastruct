package com.liujun.datastruct.base.leetcode.slide.code0992;

/**
 * 992. K 个不同整数的子数组
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int subarraysWithKDistinct(int[] A, int K) {

    if (A == null || K < 1) {
      return -1;
    }

    return count(A, K) - count(A, K - 1);
  }

  /**
   * 按频次统计
   *
   * @param array 数组
   * @param K 个数
   * @return 统计的个数
   */
  private int count(int[] array, int K) {
    int result = 0;

    int left = 0;
    int right = 0;

    // 滑动窗口
    int[] slideMap = new int[array.length + 1];
    int slideCount = 0;

    while (right < array.length) {
      int rightValue = array[right];

      // 初次操作时加加
      if (slideMap[rightValue] == 0) {
        slideCount++;
      }

      slideMap[rightValue]++;
      right++;

      // 当超过满足条件的个数个
      while (slideCount > K) {
        int leftValue = array[left];
        slideMap[leftValue]--;
        if (slideMap[leftValue] == 0) {
          slideCount--;
        }
        left++;
      }
      // 计算满足条件的个数
      result += right - left;
    }
    return result;
  }
}
