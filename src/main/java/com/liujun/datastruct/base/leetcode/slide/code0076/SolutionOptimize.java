package com.liujun.datastruct.base.leetcode.slide.code0076;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. 最小覆盖子串
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionOptimize implements SolutionInf {

  @Override
  public String minWindow(String s, String t) {

    if (s == null || t == null) {
      return "";
    }

    if (s.equals(t)) {
      return s;
    }

    int left = 0;
    int right = 0;
    char[] targetData = t.toCharArray();
    char[] srcData = s.toCharArray();

    // 用来进行查找字符是否在目标子串中
    Map<Character, Integer> targetMap = new HashMap<>(targetData.length, 1);
    for (int i = 0; i < targetData.length; i++) {
      targetMap.put(targetData[i], targetMap.getOrDefault(targetData[i], 0) + 1);
    }

    // 窗口内所有的字符串
    Map<Character, Integer> slideAllMap = new HashMap<>(targetData.length, 1);
    // 窗口内匹配的字符串
    Map<Character, Integer> slideMatch = new HashMap<>(s.length(), 1);

    String defaultValue = s + t;

    // 用于记录下当前已经匹配的字符信息,以长度为key，以字符为值
    String minChar = defaultValue;

    int minWindow = srcData.length;
    while (right < srcData.length) {
      char item = srcData[right];
      right++;

      // 将字符加入到窗口中
      slideAllMap.put(item, slideAllMap.getOrDefault(item, 0) + 1);

      // 如果当前匹配字符
      if (targetMap.containsKey(item)) {
        slideMatch.put(item, slideMatch.getOrDefault(item, 0) + 1);
        // 如果当前已经满足匹配的大小,记录下当前的数据
        while (slideMap(targetMap, slideMatch)) {
          String newMinData = new String(srcData, left, right - left);
          // 最小窗口匹配
          minWindow = Math.min(minWindow, right - left);

          if (minWindow == newMinData.length()) {
            minChar = newMinData;
          }

          // 窗口内的字符移动一位
          char itemLeft = srcData[left];
          slideAllMap.put(itemLeft, slideAllMap.get(itemLeft) - 1);
          if (slideAllMap.get(itemLeft) == 0) {
            slideAllMap.remove(itemLeft);
          }
          // 匹配字符串的移动操作
          if (slideMatch.containsKey(itemLeft)) {
            slideMatch.put(itemLeft, slideMatch.get(itemLeft) - 1);
            if (slideMatch.get(itemLeft) == 0) {
              slideMatch.remove(itemLeft);
            }
          }

          left++;
        }
      }
    }

    if (null == minChar || minChar.length() < t.length()) {
      return "";
    }

    if (defaultValue.equals(minChar)) {
      return "";
    }

    return minChar;
  }

  /**
   * 窗口的匹配检查
   *
   * @param targetMap 目标的map
   * @param slideMatch 当前窗口的map
   * @return
   */
  private boolean slideMap(Map<Character, Integer> targetMap, Map<Character, Integer> slideMatch) {
    if (targetMap.size() != slideMatch.size()) {
      return false;
    }

    // 进行完整字符的检查
    for (Map.Entry<Character, Integer> dataItem : slideMatch.entrySet()) {
      // 如果当前小于目标窗口的大小，说明不匹配
      if (dataItem.getValue() < targetMap.get(dataItem.getKey())) {
        return false;
      }
    }

    return true;
  }
}
