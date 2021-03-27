package com.liujun.datastruct.base.leetcode.slide.code0209;

/**
 * 长度最小的子数组
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 2 ms ,
 *
 * <p>在所有 Java 提交中击败了 82.07% 的用户
 *
 * <p>内存消耗： 38.1 MB , 在所有 Java 提交中击败了 97.25% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution implements SolutionInf {

  @Override
  public int minSubArrayLen(int target, int[] nums) {

    int left = 0;
    int right = 0;
    int minNum = target;
    int sumValue = 0;

    // 用于进行找到目标标识
    boolean findTarget = false;

    while (right < nums.length) {
      sumValue += nums[right];
      right++;

      while (sumValue >= target) {
        minNum = Math.min(minNum, right - left);
        findTarget = true;
        sumValue -= nums[left];
        left++;
      }
    }

    if (findTarget) {
      return minNum;
    }

    return 0;
  }
}
