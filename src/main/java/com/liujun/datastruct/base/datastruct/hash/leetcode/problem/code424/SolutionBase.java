package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code424;

import java.util.HashMap;
import java.util.Map;

/**
 * 替换最大字符串
 *
 * <p>首先想到的还是暴力解法.
 *
 * <p>以左字符为起点，作最大可能的匹配
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionBase {

  /**
   * 替换最大子串
   *
   * @param s
   * @param k
   * @return
   */
  public int characterReplacement(String s, int k) {

    if (s == null) {
      return -1;
    }

    if (k >= s.length()) {
      return s.length();
    }

    int maxDataNum = 0;
    // 统计以左字符为开始的最大匹配数
    char[] dataArrays = s.toCharArray();
    Map<Character, Integer> dataCount = new HashMap<>(dataArrays.length + 1);
    for (int i = 0; i < dataArrays.length; i++) {
      // 子串的匹配操作
      for (int j = i + k; j < dataArrays.length; j++) {
        dataCount.clear();
        // 做字符的统计操作
        for (int l = i; l <= j; l++) {
          Integer count = dataCount.get(dataArrays[l]);
          if (count == null) {
            dataCount.put(dataArrays[l], 1);
          } else {
            dataCount.put(dataArrays[l], count + 1);
          }
        }

        int maxNum = 0;
        Character dataChat = null;
        for (Map.Entry<Character, Integer> entryItem : dataCount.entrySet()) {
          int currValue = entryItem.getValue();
          if (maxNum < currValue) {
            maxNum = currValue;
            dataChat = entryItem.getKey();
          }
        }

        // 以开始字符为当前可替换字符进行计算
        Integer startCount = dataCount.remove(dataChat);

        // 当其他字符大于可替换字符时，则退出第二重复循环
        int maxOther = 0;
        for (Map.Entry<Character, Integer> entryItem : dataCount.entrySet()) {
          maxOther += entryItem.getValue();
        }

        if (maxOther > k) {
          break;
        }

        int countNum = startCount;
        // 计算替换后最长长度
        for (Map.Entry<Character, Integer> entryItem : dataCount.entrySet()) {
          countNum += entryItem.getValue();
        }

        if (maxDataNum < countNum) {
          maxDataNum = countNum;
        }
      }
    }

    return maxDataNum;
  }
}
