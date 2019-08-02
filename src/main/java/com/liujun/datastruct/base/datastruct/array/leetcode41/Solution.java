package com.liujun.datastruct.base.datastruct.array.leetcode41;

/**
 * 求最小正整数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/27
 */
public class Solution {

  public int firstMissingPositive(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 1;
    }

    int runNum = nums.length;
    int maxValue = runNum + 1;
    int[] buckets = new int[maxValue];

    //将数据放入到桶中
    for (int i = 0; i < maxValue; i++) {
      if (i < runNum && nums[i] > 0 && nums[i] < maxValue) {
        buckets[nums[i] - 1] = nums[i];
      }
    }

    int result = -1;
    //找到最连续中缺失的数据
    for (int i = 0; i < maxValue; i++) {
      if (buckets[i] == 0) {
        result = i + 1;
        break;
      }
    }

    return result;
  }
}
