package com.liujun.datastruct.base.search.binarysearch2.leetcode162;

/**
 * 寻找峰值
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public int findPeakElement(int[] nums) {
    if (nums == null || nums.length < 1) {
      return -1;
    }

    if (nums.length == 1) {
      return 0;
    }

    int low = 0, mid, high = nums.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 如果当前满足增长，则继续在右区间查找
      if (mid < high && nums[mid] < nums[mid + 1]) {
        low = mid + 1;
      }
      // 如果当前不满足增长，则以当前为截止
      else {
        high = mid;
      }
    }

    // 当遍历结束，上一次的开始元素即为最后结果
    return low;
  }
}
