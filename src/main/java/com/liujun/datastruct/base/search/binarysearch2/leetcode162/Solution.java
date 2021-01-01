package com.liujun.datastruct.base.search.binarysearch2.leetcode162;

/**
 * 寻找峰值
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int findPeakElement(int[] nums) {
    if (nums == null || nums.length < 1) {
      return -1;
    }

    if (nums.length == 1) {
      return 0;
    }

    int low = 0, mid, high = nums.length - 1;

    while (low < high) {
      mid = low + (high - low) / 2;
      if (mid < high && nums[mid] < nums[mid + 1]) {
        low = mid + 1;
      } else {
        high = mid;
      }
    }

    return low;
  }
}
