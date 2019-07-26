package com.liujun.datastruct.base.datastruct.array.leetcode169;

/**
 * 求众数的问题求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/26
 */
public class MajoritySolution {

  public int majorityElement(int[] nums) {
    if (nums.length == 1) {
      return nums[0];
    }

    int result = 0;

    for (int i = 0; i < nums.length; i++) {
      int value = nums[i];
      int count = 0;

      for (int j = 0; j < nums.length; j++) {
        if (nums[j] == value) {
          count++;
        }
      }

      if (count > nums.length / 2) {
        result = value;
        break;
      }
    }

    return result;
  }
}
