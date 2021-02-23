package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code217;

import java.util.HashSet;
import java.util.Set;

/**
 * code 217，做重复判断的检查
 *
 * <p>执行结果： 通过 显示详情 执行用时： 8 ms ,
 *
 * <p>在所有 Java 提交中击败了 48.24% 的用户
 *
 * <p>内存消耗： 45.1 MB , 在所有 Java 提交中击败了 14.40% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public boolean containsDuplicate(int[] nums) {

    if (null == nums || nums.length < 2) {
      return false;
    }

    boolean exists = false;
    Set<Integer> dataHash = new HashSet<>(nums.length);

    for (int dataValue : nums) {
      // 如果存在，则返回
      if (dataHash.contains(dataValue)) {
        exists = true;
        break;
      }
      // 如果不存在，则加入
      else {
        dataHash.add(dataValue);
      }
    }

    return exists;
  }
}
