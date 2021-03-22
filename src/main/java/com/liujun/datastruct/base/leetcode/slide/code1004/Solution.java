package com.liujun.datastruct.base.leetcode.slide.code1004;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /**
   * 最大连续1的个数
   *
   * @param A
   * @param K
   * @return
   */
  public int longestOnes(int[] A, int K) {
    if (A.length < K) {
      return A.length;
    }

    int left = 0;
    int right = 0;
    int dataLength = A.length;
    int slideMax = 0;
    // 统计窗口内字符数
    int[] slideNum = new int[2];

    while (right < dataLength) {
      slideNum[A[right]]++;
      right++;
      slideMax = Math.max(slideMax, slideNum[1]);

      // 如果窗口大于通话的替换数，则左指针进1
      if (right - left > slideMax + K) {
        slideNum[A[left]]--;
        left++;
      }
    }

    return right - left;
  }
}
