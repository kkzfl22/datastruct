package com.liujun.datastruct.base.datastruct.array.leetcode169;

import java.util.HashMap;
import java.util.Map;

/**
 * 求众数的，第二种方法求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/26
 */
public class MajoritySolution2 {

  public int majorityElement(int[] nums) {
    if (nums.length == 1) {
      return nums[0];
    }

    Map<Integer, Integer> majorityMap = new HashMap<>(nums.length);

    Integer value = 0;
    int resultValue = 0;

    for (int i = 0; i < nums.length; i++) {
      value = majorityMap.get(nums[i]);

      if (null == value) {
        majorityMap.put(nums[i], 1);
      } else {
        majorityMap.put(nums[i], value + 1);
      }

      if (null != value && value >= nums.length / 2) {
        resultValue = nums[i];
        break;
      }
    }

    return resultValue;
  }
}
