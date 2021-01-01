package com.liujun.datastruct.base.search.binarysearch2.leetcode167;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int[] twoSum(int[] numbers, int target) {

    if (null == numbers || numbers.length < 2) {
      return new int[0];
    }

    for (int i = 0; i < numbers.length; i++) {
      int findIndex = this.binaryFind(numbers, target - numbers[i], i);
      if (findIndex != -1) {
        if (findIndex > i) {
          return new int[] {i + 1, findIndex + 1};
        } else {
          return new int[] {findIndex + 1, i + 1};
        }
      }
    }

    return new int[0];
  }

  private int binaryFind(int[] nums, int target, int currIndex) {
    int low = 0, mid, high = nums.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      if (nums[mid] == target && currIndex != mid) {
        return mid;
      } else if (nums[mid] < target) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return -1;
  }
}
