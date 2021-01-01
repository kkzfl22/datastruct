package com.liujun.datastruct.base.search.binarysearch2.leetcode704;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int search(int[] nums, int target) {

    if (null == nums) {
      return -1;
    }

    int low = 0, mid, high = nums.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;
      if (target == nums[mid]) {
        return mid;
      }
      // 如果中间值比目标值小，则在右区间查找
      else if (nums[mid] < target) {
        low = mid + 1;
      }
      // 如果中间值比目标值大，则在左区间查找
      else {
        high = mid - 1;
      }
    }

    return -1;
  }
}
