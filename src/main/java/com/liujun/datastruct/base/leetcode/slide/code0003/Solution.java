package com.liujun.datastruct.base.leetcode.slide.code0003;

import java.util.HashMap;
import java.util.Map;

/**
 * 3. 无重复字符的最长子串
 *
 * <p>0.1版本
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 9 ms ,
 *
 * <p>在所有 Java 提交中击败了 40.54% 的用户
 *
 * <p>内存消耗： 38.4 MB , 在所有 Java 提交中击败了 88.68% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution implements SolutionInf {

  @Override
  public int lengthOfLongestSubstring(String s) {

    if (s == null || s.length() < 1) {
      return 0;
    }

    if (s.length() == 1) {
      return 1;
    }

    int left = 0;
    int right = 0;
    int maxSolid = 0;

    char[] dataValue = s.toCharArray();
    Map<Character, Integer> dataHash = new HashMap<>(256, 1);
    while (right < dataValue.length) {
      dataHash.put(dataValue[right], dataHash.getOrDefault(dataValue[right], 0) + 1);

      // 当发生重复时，窗口左侧坐标向前进一
      while (dataHash.get(dataValue[right]) > 1) {
        if (dataHash.get(dataValue[left]) == 1) {
          dataHash.remove(dataValue[left]);
        } else {
          dataHash.put(dataValue[left], dataHash.get(dataValue[left]) - 1);
        }

        left++;
      }
      right++;

      maxSolid = Math.max(maxSolid, right - left);
    }

    return maxSolid;
  }
}
