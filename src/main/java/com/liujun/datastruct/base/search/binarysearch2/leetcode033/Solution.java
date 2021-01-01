package com.liujun.datastruct.base.search.binarysearch2.leetcode033;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int search(int[] nums, int target) {

    if (nums == null || nums.length <= 0) {
      return -1;
    }

    int low = 0, mid, high = nums.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 最左侧匹配的情况
      if (nums[low] == target) {
        return low;
      }
      // 中间匹配的情况
      else if (nums[mid] == target) {
        return mid;
      }
      // 右侧匹配的情况
      else if (nums[high] == target) {
        return high;
      }

      // 如果num[mid]>num[low]，说明左区间有序
      if (nums[mid] > nums[low]) {
        // 如果num[mid] < target,说明在右区间
        if (nums[mid] < target) {
          low = mid + 1;
        }
        // 如果num[mid] > target,说明在左区间
        else {
          // 如果nums[low] < target，则在左区间中
          if (nums[low] < target) {
            high = mid - 1;
          }
          // 如果nums[low] > target，则在右区间中
          else {
            low = mid + 1;
          }
        }
      }
      // 如果num[mid]<=num[low]，说明右区间有序
      else {
        // 右区间有序，并且目标值大于中间值,存在两种情况
        if (nums[mid] < target) {
          // 如果当前查找值比结束的值还要大，说明在左区间
          if (nums[high] < target) {
            high = mid - 1;
          }
          // 如果当前查找值比结束的值要小，说明在右区间
          else {
            low = mid + 1;
          }
        }
        // 右区间有序，并且nums[mid] > target，说明仅存在于左区间的可能
        else {
          high = mid - 1;
        }
      }
    }

    return -1;
  }
}
