package com.liujun.datastruct.base.leetcode.slide.code0992;

import org.junit.Assert;
import org.junit.Test;

/**
 * 992. K 个不同整数的子数组
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情
 *
 * <p>执行用时： 5 ms ,
 *
 * <p>在所有 Java 提交中击败了 72.69% 的用户
 *
 * <p>内存消耗： 41.9 MB , 在所有 Java 提交中击败了 28.59% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testSubarraysWithKDistinct() {
    this.assertCheck(new int[] {1, 2}, 1, 2);
    this.assertCheck(new int[] {1, 2, 1, 2, 3}, 2, 7);
    this.assertCheck(new int[] {1, 2, 1, 3, 4}, 3, 3);
    this.assertCheck(new int[] {2, 1, 2, 1, 2}, 2, 10);
    this.assertCheck(
        new int[] {
          27, 27, 43, 28, 11, 20, 1, 4, 49, 18, 37, 31, 31, 7, 3, 31, 50, 6, 50, 46, 4, 13, 31, 49,
          15, 52, 25, 31, 35, 4, 11, 50, 40, 1, 49, 14, 46, 16, 11, 16, 39, 26, 13, 4, 37, 39, 46,
          27, 49, 39, 49, 50, 37, 9, 30, 45, 51, 47, 18, 49, 24, 24, 46, 47, 18, 46, 52, 47, 50, 4,
          39, 22, 50, 40, 3, 52, 24, 50, 38, 30, 14, 12, 1, 5, 52, 44, 3, 49, 45, 37, 40, 35, 50,
          50, 23, 32, 1, 2
        },
        20,
        149);
  }

  /**
   * 不同整数的子数组
   *
   * @param array 源数组
   * @param K 个数
   * @param target 目标
   */
  private void assertCheck(int[] array, int K, int target) {
    Solution instance = new Solution();
    int result = instance.subarraysWithKDistinct(array, K);
    Assert.assertEquals(result, target);
    System.out.println("-------------");
  }
}
