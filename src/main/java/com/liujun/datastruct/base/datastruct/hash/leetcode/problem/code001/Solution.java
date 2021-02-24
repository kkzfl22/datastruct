package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code001;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int[] twoSum(int[] nums, int target) {
    if (nums.length < 2) {
      return new int[0];
    }

    // 1,将数据全部录入至哈希表中
    Map<Integer, Integer> dataMap = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int countValue = target - nums[i];
      Integer value = dataMap.get(countValue);

      if (null == value) {
        dataMap.put(nums[i], i);
      } else {
        return new int[] {value, i};
      }
    }

    return new int[0];
  }
}
