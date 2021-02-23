package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code136;

import java.util.HashMap;
import java.util.Map;

/**
 * 只出现一次的数字
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public int singleNumber(int[] nums) {

    if (null == nums) {
      return 0;
    }

    if (nums.length < 2) {
      return nums[0];
    }

    int result = 0;

    // 其利用的原理就是当两个数相同时，使用^操作
    // 异或、“位异或"按位“异或”操作符，如果两个数的二进制，相同位数只有一个是1，则该位结果是1，否则是0
    // 当两个数相同时，使用异或操作后，则两个数归为0
    // 异或操作，按位操作，相同的位，合并后变0，不同的位数，在异或后被保留了下来
    for (int i = 0; i < nums.length; i++) {
      result = nums[i] ^ result;
    }

    return result;
  }
}
