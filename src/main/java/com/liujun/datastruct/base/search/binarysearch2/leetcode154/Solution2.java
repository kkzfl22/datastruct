package com.liujun.datastruct.base.search.binarysearch2.leetcode154;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public int findMin(int[] nums) {

    if (nums == null || nums.length == 0) {
      return -1;
    }
    if (nums.length <= 1) {
      return nums[0];
    }

    int low = 0, high = nums.length - 1;
    while (low < high) {
      int mid = low + (high - low) / 2;
      if (nums[mid] > nums[high]) {
        low = mid + 1;
      } else if (nums[mid] < nums[high]) {
        high = mid;
      } else {
        high--;
      }
    }

    return nums[low];
  }
}
