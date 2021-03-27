package com.liujun.datastruct.base.leetcode.slide.code0076;

/**
 * 76. 最小覆盖子串
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionOptimizeArray implements SolutionInf {

  @Override
  public String minWindow(String s, String t) {
    // 定义一个填充数组
    int[] need = new int[123];
    int count = 0;
    // 记录下每个字符的数量
    for (int i = 0; i < t.length(); ++i) {
      need[t.charAt(i)]++;
      count++;
    }

    // 最小字符的长度
    int minLength = Integer.MAX_VALUE;
    String res = "";
    int left = 0;
    int right = 0;
    while (right < s.length()) {
      char c = s.charAt(right);
      // 当遍历到一个后，则在匹配字符的数量上减1
      if (need[c] > 0) {
        count--;
      }
      // 目标数量上减1
      need[c]--;
      // 当所有字符都匹配后
      if (count == 0) {
        // 检查当前字符跳过不匹配的字符
        while (need[s.charAt(left)] < 0) {
          need[s.charAt(left)]++;
          left++;
        }
        // 计算窗口内的最小字符
        if (right - left + 1 < minLength) {
          minLength = right - left + 1;
          res = s.substring(left, right + 1);
        }
        need[s.charAt(left)]++;
        left++;
        count++;
      }
      right++;
    }
    return res;
  }
}
