package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code424;

/**
 * 替换最大字符串
 *
 * <p>使用滑动窗口来进行求解
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /** 字符A */
  private static final char DATA_A = 'A';

  /**
   * 替换最大子串
   *
   * @param s
   * @param k
   * @return
   */
  public int characterReplacement(String s, int k) {

    if (s.length() <= 1) {
      return s.length();
    }

    // 滑动窗口位置定义
    int start = 0;
    int end = 0;

    int maxSlideRepeat = 0;
    int[] dataCount = new int[26];

    char[] dataContent = s.toCharArray();
    while (end < dataContent.length) {
      // 1，记录下当前窗口内的统计
      dataCount[dataContent[end] - DATA_A]++;
      // 窗口内最大生重复数
      maxSlideRepeat = Math.max(maxSlideRepeat, dataCount[dataContent[end] - DATA_A]);
      end++;

      // 窗口左指针是否需要移动
      if (end - start > maxSlideRepeat + k) {
        // 将滑动窗口的左边指向的数据减一操作
        dataCount[dataContent[start] - DATA_A]--;
        start++;
      }
    }

    return end - start;
  }
}
