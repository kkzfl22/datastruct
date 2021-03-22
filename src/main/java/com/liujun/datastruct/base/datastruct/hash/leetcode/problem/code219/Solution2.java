package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code219;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，
 *
 * <p>并且 i 和 j 的差的 绝对值 至多为 k。
 *
 * @author liujun
 * @version 0.0.1
 */
class Solution2 {
  /**
   * 进行检查
   *
   * @param nums
   * @param k
   * @return
   */
  public boolean containsNearbyDuplicate(int[] nums, int k) {

    if (nums == null || nums.length < 1) {
      return false;
    }

    Map<Integer, Integer> dataCount = new HashMap<>(nums.length);

    for (int i = 0; i < nums.length; i++) {
      Integer index = dataCount.get(nums[i]);

      if (null == index) {
        dataCount.put(nums[i], i);
        continue;
      }

      if (Math.abs(i - index) <= k) {
        return true;
      }

      dataCount.put(nums[i], i);
    }

    return false;
  }
}
