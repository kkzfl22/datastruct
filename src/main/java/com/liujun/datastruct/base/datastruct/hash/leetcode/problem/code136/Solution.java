package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code136;

import java.util.HashMap;
import java.util.Map;

/**
 * 只出现一次的数字
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int singleNumber(int[] nums) {

    if (null == nums) {
      return -1;
    }

    if (nums.length < 2) {
      return nums[0];
    }

    Map<Integer, Integer> dataHash = new HashMap<>(nums.length / 2);

    for (int numItem : nums) {
      // 当数据仅一次时，放入
      if (!dataHash.containsKey(numItem)) {
        dataHash.put(numItem, 1);
      }
      // 超过一次，则删除
      else {
        dataHash.remove(numItem);
      }
    }

    for (Map.Entry<Integer, Integer> dataItem : dataHash.entrySet()) {
      return dataItem.getKey();
    }

    return -1;
  }
}
