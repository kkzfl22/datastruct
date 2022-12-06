package com.liujun.datastruct.base.leetcode.slide.code1658;

import java.util.HashMap;
import java.util.Map;

/**
 * 1658. 将 x 减到 0 的最小操作数
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 89 ms ,
 *
 * <p>在所有 Java 提交中击败了 19.59% 的用户
 *
 * <p>内存消耗： 50.6 MB , 在所有 Java 提交中击败了 35.96% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int minOperations(int[] nums, int x) {
    if (nums.length < 1) {
      return -1;
    }

    int len = nums.length;

    // 计算前缀和
    int[] prefixSum = new int[len + 1];
    for (int i = 0; i < len; i++) {
      prefixSum[i + 1] = prefixSum[i] + nums[i];
    }

    // 计算后缀和
    Map<Integer, Integer> suffixMap = new HashMap<>(nums.length, 1);
    int[] suffixSum = new int[len + 1];
    suffixMap.put(0, 0);
    int index = 1;
    for (int i = len - 1; i >= 0; i--) {
      // 倒数第一与倒数第二相加
      suffixSum[index] = suffixSum[index - 1] + nums[i];
      suffixMap.put(suffixSum[index], index);
      index++;
    }

    // 进行结果的检查
    if (prefixSum[len] < x || suffixSum[len] < x) {
      return -1;
    }

    int res = Integer.MAX_VALUE;
    for (int i = 0; i < len + 1; i++) {
      int preCount = prefixSum[i];
      if (suffixMap.containsKey(x - preCount)) {
        res = Math.min(res, suffixMap.get(x - preCount) + i);
      }
    }

    return res == Integer.MAX_VALUE ? -1 : res;
  }
}
