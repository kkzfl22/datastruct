package com.liujun.datastruct.base.sort.leetcode.code75.implement;

/**
 * sort colors
 *
 * <p>使用计数排序算法来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/06
 */
public class SolutionCode75Other implements SolutionCode75Inf {

  public void sortColors(int[] nums) {
    int l = 0, i = 0, r = nums.length - 1;
    while (i <= r) {
      if (i < l || nums[i] == 1) {
        i += 1;
      } else if (nums[i] == 0) {
        swap(nums, i, l++);
      } else if (nums[i] == 2) {
        swap(nums, i, r--);
      }
    }
  }

  public void swap(int[] nums, int i, int j) {
    int t = nums[i];
    nums[i] = nums[j];
    nums[j] = t;
  }
}
