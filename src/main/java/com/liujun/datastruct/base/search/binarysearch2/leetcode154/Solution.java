package com.liujun.datastruct.base.search.binarysearch2.leetcode154;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int findMin(int[] nums) {

    if (nums == null || nums.length == 0) {
      return -1;
    }
    if (nums.length <= 1) {
      return nums[0];
    }

    int low = 0, mid, high = nums.length - 1;
    int result = nums[0];

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 检查是否为左边区间有序的情况
      if (nums[low] < nums[mid]) {
        if (nums[low] < nums[high]) {
          return nums[low];
        } else {
          low = mid + 1;
        }
      }
      // 检查是否为右区间有序的情况
      else if (nums[low] > nums[mid]) {
        // 前一个元素需要大于当前元素
        if (mid != 0 && nums[mid - 1] > nums[mid]) {
          return nums[mid];
        } else {
          if (nums[mid] > nums[high]) {
            result = nums[high];
          }
          high = mid - 1;
        }
      }
      // 当区间中间值与左区间存在相同时，则在右区间中查找
      else {
        // 如果中间件大于左边的，则在右区间查找
        if (nums[mid] > nums[high]) {
          low = mid + 1;
        } else if (nums[mid] < nums[high]) {
          // 前一个元素需要大于当前元素
          return nums[mid];
        } else {
          low = low + 1;
          result = nums[mid];
        }
      }
    }

    return result;
  }
}
