package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code349;

import java.util.Arrays;

/**
 * 最快速度的解决方案
 *
 * <p>使用一个数组来替代map,在数据范围很小时，是比较好的方案。
 *
 * <p>还是拿空间换时间的思路
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public int[] intersection(int[] nums1, int[] nums2) {

    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }

    int minLength = Math.min(nums1.length, nums2.length);
    int[] dataResult = new int[minLength];
    boolean[] dataContext = new boolean[1024];

    for (int context : nums1) {
      dataContext[context] = true;
    }

    int outLen = 0;

    for (int i = 0; i < nums2.length; i++) {
      if (dataContext[nums2[i]]) {
        dataResult[outLen++] = nums2[i];
        // 用于将数据记录一次
        dataContext[nums2[i]] = false;
      }
    }

    return Arrays.copyOf(dataResult, outLen);
  }
}
