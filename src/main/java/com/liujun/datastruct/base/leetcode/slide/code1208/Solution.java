package com.liujun.datastruct.base.leetcode.slide.code1208;

/**
 * 尽可能使字符串相等
 *
 * <p>执行结果： 通过 显示详情
 *
 * <p>执行用时： 7 ms ,
 *
 * <p>在所有 Java 提交中击败了 48.69% 的用户
 *
 * <p>内存消耗： 38.1 MB , 在所有 Java 提交中击败了 99.80% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution implements SolutionInf {

  @Override
  public int equalSubstring(String s, String t, int maxCost) {
    if (s == null || s.length() < 1) {
      return -1;
    }
    if (t == null || t.length() < 1) {
      return -1;
    }

    char[] srcChar = s.toCharArray();
    char[] targetChar = t.toCharArray();

    int left = 0;
    int right = 0;

    int slideMax = 0;
    int parseCount = 0;
    int slideNum = 0;

    while (right < srcChar.length) {
      int countMine = Math.abs(targetChar[right] - srcChar[right]);
      parseCount = countMine + parseCount;
      slideNum = slideNum + 1;

      if (parseCount > maxCost) {
        int leftCount = Math.abs(targetChar[left] - srcChar[left]);
        parseCount = parseCount - leftCount;
        left++;
        slideNum = slideNum - 1;
      }

      slideMax = Math.max(slideMax, slideNum);

      right++;
    }

    return slideMax;
  }
}
