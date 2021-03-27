package com.liujun.datastruct.base.leetcode.slide.code0076;

/**
 * 76. 最小覆盖子串
 *
 * <p>目标字符串存在于重复，这是最麻烦处理的，记得审题
 *
 * <p>执行用时： 2 ms ,
 *
 * <p>在所有 Java 提交中击败了 100.00% 的用户
 *
 * <p>内存消耗： 38.7 MB , 在所有 Java 提交中击败了 73.44% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionOptimize2 implements SolutionInf {

  @Override
  public String minWindow(String s, String t) {

    if (s == null || t == null) {
      return "";
    }

    if (s.equals(t)) {
      return t;
    }

    char[] srcData = s.toCharArray();
    char[] tarData = t.toCharArray();

    int[] targetMap = new int[128];
    int[] slideCountMap = new int[128];

    // 对目标中的频数做统计
    for (int i = 0; i < tarData.length; i++) {
      targetMap[tarData[i]]++;
    }

    int left = 0;
    int right = 0;
    int slideCount = 0;

    int minLen = srcData.length + 1;
    int minStart = 0;

    // [left,right)
    while (right < srcData.length) {
      // 1,跳过无用的数据
      char rightItem = srcData[right];
      if (targetMap[rightItem] == 0) {
        right++;
        continue;
      }

      // 检查数据是否在目标数据区间，超过则交给滑动窗口再做统计
      if (slideCountMap[rightItem] < targetMap[rightItem]) {
        slideCount++;
      }

      slideCountMap[rightItem]++;
      right++;

      // 当数据内匹配完整数据时，则开始收缩左指针
      while (slideCount == tarData.length) {
        char leftItem = srcData[left];

        if (right - left < minLen) {
          minLen = right - left;
          minStart = left;
        }

        // 1,跳过无用数据
        if (targetMap[leftItem] == 0) {
          left++;
          continue;
        }

        // 当数据匹配时，则进行计数减
        if (slideCountMap[leftItem] == targetMap[leftItem]) {
          slideCount--;
        }

        slideCountMap[leftItem]--;
        left++;
      }
    }

    if (minLen == srcData.length + 1) {
      return "";
    }

    return s.substring(minStart, minStart + minLen);
  }
}
