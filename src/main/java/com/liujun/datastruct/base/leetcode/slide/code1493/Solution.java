package com.liujun.datastruct.base.leetcode.slide.code1493;

/**
 * 1493. 删掉一个元素以后全为 1 的最长子数组
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /**
   * 1493. 删掉一个元素以后全为 1 的最长子数组
   *
   * @param nums
   * @return
   */
  public int longestSubarray(int[] nums) {

    if (nums.length < 1) {
      return -1;
    }

    int left = 0;
    int right = 0;
    int maxOneNum = 0;
    int[] numCount = new int[2];

    while (right < nums.length) {
      numCount[nums[right]]++;

      // 当窗口内为0的元素多于1时，则
      if (numCount[0] > 1) {
        numCount[nums[left]]--;
        left++;
      }

      maxOneNum = Math.max(maxOneNum, right - left);
      right++;
    }

    if (maxOneNum == nums.length) {
      return maxOneNum - 1;
    }

    return maxOneNum;
  }
}
