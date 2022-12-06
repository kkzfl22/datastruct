package com.liujun.datastruct.base.leetcode.slide.code1658;

/**
 * 1658. 将 x 减到 0 的最小操作数
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 4 ms ,在所有 Java 提交中击败了 100.00% 的用户
 *
 * <p>内存消耗： 48.3 MB , 在所有 Java 提交中击败了 90.94% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public int minOperations(int[] nums, int x) {
    if (nums.length < 1) {
      return -1;
    }

    int len = nums.length;

    int left = 0;
    int right = 0;
    int sum = 0;
    for (int i = 0; i < len; i++) {
      sum += nums[i];
    }

    if (sum < x) {
      return -1;
    }

    if (sum == x) {
      return len;
    }

    int rest = 0;
    int rsSum = sum - x;
    int count = -1;
    while (right < len) {
      rest += nums[right];
      while (rest > rsSum) {
        rest -= nums[left];
        left++;
      }
      if (rest == rsSum) {
        count = Math.max(count, right - left + 1);
      }
      right++;
    }

    if (count == -1) {
      return -1;
    }

    return len - count;
  }
}
