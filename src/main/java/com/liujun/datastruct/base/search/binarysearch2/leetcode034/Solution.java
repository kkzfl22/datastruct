package com.liujun.datastruct.base.search.binarysearch2.leetcode034;

/**
 * 求开始位置与结束位置
 *
 * <p>使用二分法，先找左边，再找右边
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  private static final int[] RESULT = {-1, -1};

  public int[] searchRange(int[] nums, int target) {

    if (nums == null || nums.length < 1) {
      return RESULT;
    }

    int low = 0, mid, high = nums.length - 1;
    int[] dataResult = {-1, -1};

    while (low <= high) {
      mid = low + (high - low) / 2;

      if (nums[mid] == target) {
        // 1,找到开始位置
        if (mid == 0 || nums[mid - 1] != target) {
          dataResult[0] = mid;
          break;
        } else {
          high = mid - 1;
        }
      }
      // 如果区间中间值，大于目标值，则说明在左区间查找
      else if (nums[mid] > target) {
        high = mid - 1;
      }
      // 如果区间中间值小于目标值，则说明在右区间查找
      else {
        low = mid + 1;
      }
    }
    low = 0;
    high = nums.length - 1;

    // 再进行结束位置的查找
    while (low <= high) {
      mid = low + (high - low) / 2;
      if (nums[mid] == target) {
        // 1,找到结束的位置
        if (mid == high || nums[mid + 1] != target) {
          dataResult[1] = mid;
          break;
        } else {
          low = mid + 1;
        }
      }
      // 如果区间中间值，大于目标值，则说明在左区间查找
      else if (nums[mid] > target) {
        high = mid - 1;
      }
      // 如果区间中间值小于目标值，则说明在右区间查找
      else {
        low = mid + 1;
      }
    }

    return dataResult;
  }
}
