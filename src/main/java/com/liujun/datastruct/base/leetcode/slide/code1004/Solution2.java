package com.liujun.datastruct.base.leetcode.slide.code1004;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

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
    // 可替换数，控制在K以内
    int repeatNum = 0;

    while (right < dataLength) {
      if (A[right] == 0) {
        repeatNum++;
      }

      if (repeatNum > K) {
        left++;
        if (A[left - 1] == 0) {
          repeatNum--;
        }
      }

      right++;
    }

    return right - left;
  }
}
