package com.liujun.datastruct.base.leetcode.slide.code0567;

/**
 * 567. 字符串的排列
 *
 * <p>permutation
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 3 ms ,
 *
 * <p>在所有 Java 提交中击败了 99.73% 的用户
 *
 * <p>内存消耗： 38.2 MB , 在所有 Java 提交中击败了 97.47% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /**
   * 排列的验证
   *
   * @param s1 子串
   * @param s2 完整字符串
   * @return true是排列之一，false，非排列之一
   */
  public boolean checkInclusion(String s1, String s2) {

    if (s1 == null || s1.length() < 1) {
      return false;
    }
    if (s2 == null || s2.length() < 1) {
      return false;
    }

    char[] srcData = s2.toCharArray();
    char[] targetData = s1.toCharArray();

    int[] tarMap = new int[128];
    int[] slideMap = new int[128];

    for (int i = 0; i < targetData.length; i++) {
      tarMap[targetData[i]]++;
    }

    int left = 0;
    int right = 0;

    int slideCount = 0;

    while (right < srcData.length) {
      char rightItem = srcData[right];

      // 1,跳过无用的记录
      if (tarMap[rightItem] == 0) {
        right++;
        continue;
      }

      // 当字符发生匹配时，匹配的计数加1
      if (slideMap[rightItem] < tarMap[rightItem]) {
        slideCount++;
      }

      slideMap[rightItem]++;
      right++;

      // 当字符完全匹配时
      while (slideCount == targetData.length) {

        if (right - left == targetData.length) {
          return true;
        }

        char leftItem = srcData[left];

        if (tarMap[leftItem] == 0) {
          left++;
          continue;
        }

        // 当窗口内的字符匹配时，执行计数减1
        if (slideMap[leftItem] == tarMap[leftItem]) {
          slideCount--;
        }

        slideMap[leftItem]--;
        left++;
      }
    }

    return false;
  }
}
