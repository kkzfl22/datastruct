package com.liujun.datastruct.base.leetcode.slide.code0003;

/**
 * 3. 无重复字符的最长子串
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 2 ms ,
 *
 * <p>在所有 Java 提交中击败了 100.00% 的用户
 *
 * <p>内存消耗： 38.3 MB , 在所有 Java 提交中击败了 95.83% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionOptimize implements SolutionInf {

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
    int[] dataCount = new int[256];
    while (right < dataValue.length) {
      char dataItem = dataValue[right];
      dataCount[dataItem]++;

      // 当发生重复时，窗口左侧坐标向前进一
      while (dataCount[dataItem] > 1) {
        dataCount[dataValue[left]]--;
        left++;
      }
      right++;

      maxSolid = Math.max(maxSolid, right - left);
    }

    return maxSolid;
  }
}
