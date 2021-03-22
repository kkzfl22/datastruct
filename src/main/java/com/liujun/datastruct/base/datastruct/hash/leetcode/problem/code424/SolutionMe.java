package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code424;

import java.util.HashMap;
import java.util.Map;

/**
 * 替换最大字符串
 *
 * <p>使用滑动窗口来进行求解
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionMe {

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

    Map<Character, Integer> slideMap = new HashMap<>(26, 1);
    char[] dataTmp = s.toCharArray();
    // 窗口内重复元素的个数
    int slideSize = 0;

    while (end < dataTmp.length) {
      slideMap.put(dataTmp[end], slideMap.getOrDefault(dataTmp[end], 0) + 1);
      // 当前的窗口内最大的重复元素的个数
      slideSize = Math.max(slideSize, slideMap.get(dataTmp[end]));
      end++;

      // 当窗口的大小超过了重复元素个数加替换数,则左窗口移动
      if (end - start > slideSize + k) {
        slideMap.put(dataTmp[start], slideMap.get(dataTmp[start]) - 1);
        start++;
      }
    }
    return end - start;
  }
}
