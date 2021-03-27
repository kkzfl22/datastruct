package com.liujun.datastruct.base.leetcode.slide.code0076;

/**
 * 76. 最小覆盖子串
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 3 ms ,
 *
 * <p>在所有 Java 提交中击败了 96.53% 的用户
 *
 * <p>内存消耗： 38.7 MB , 在所有 Java 提交中击败了 71.83% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionOptimize3 implements SolutionInf {

  @Override
  public String minWindow(String s, String t) {

    if (s == null || t == null) {
      return "";
    }

    if (s.equals(t)) {
      return s;
    }

    char[] targetData = t.toCharArray();
    char[] srcData = s.toCharArray();

    // 字符的频数数组
    int[] targetMap = new int[128];
    // 滑动窗口内字符串的频繁数组
    int[] slideMap = new int[128];

    for (int i = 0; i < targetData.length; i++) {
      targetMap[targetData[i]]++;
    }

    // 用于记录下当前已经匹配的字符信息,以长度为key，以字符为值
    int minLen = s.length() + 1;
    int begin = 0;

    // 滑动窗口内部包含多少T中的字符，对应字符频数超过不重复计算
    int slideCount = 0;

    int left = 0;
    int right = 0;

    // [left,right)左闭右开区间，
    while (right < srcData.length) {
      char rightItem = srcData[right];

      // 如果字符串的频数为0，则字符直接移动到下一位，用于跳过不在区间的字符
      if (targetMap[rightItem] == 0) {
        right++;
        continue;
      }

      //仅计数在范围以内的数据,超过的，则不记录。
      if (slideMap[rightItem] < targetMap[rightItem]) {
        slideCount++;
      }
      slideMap[rightItem]++;
      right++;

      // 当滑动窗口的字符等于目标字符次数时,进行左移操作
      while (slideCount == targetData.length) {

        if (right - left < minLen) {
          minLen = right - left;
          begin = left;
        }

        // 如果字符串的频数为0，则字符直接移动到下一位，用于跳过不在区间的字符
        char leftDatum = srcData[left];
        if (targetMap[leftDatum] == 0) {
          left++;
          continue;
        }

        char leftItem = srcData[left];
        // 当字符与目标字符的次数相同时才进行目标的减1操作
        if (slideMap[leftItem] == targetMap[leftItem]) {
          slideCount--;
        }

        slideMap[leftItem]--;
        left++;
      }
    }

    if (minLen == s.length() + 1) {
      return "";
    }

    return s.substring(begin, begin + minLen);
  }
}
