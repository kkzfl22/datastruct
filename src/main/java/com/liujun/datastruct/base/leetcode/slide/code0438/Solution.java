package com.liujun.datastruct.base.leetcode.slide.code0438;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 找到字符串中所有字母异位词
 *
 * <p>子串，强调的是连续的字符串
 *
 * <p>执行结果： 通过
 *
 * <p>显示详情 执行用时： 4 ms ,
 *
 * <p>在所有 Java 提交中击败了 99.91% 的用户
 *
 * <p>内存消耗： 39.5 MB , 在所有 Java 提交中击败了 58.44% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public List<Integer> findAnagrams(String s, String p) {

    if (s == null || "".equals(s)) {
      return Collections.emptyList();
    }
    if (p == null || "".equals(p)) {
      return Collections.emptyList();
    }

    List<Integer> resultList = new ArrayList<>();

    char[] srcData = s.toCharArray();
    char[] tarData = p.toCharArray();

    int[] tarMap = new int[128];
    int[] slideMap = new int[128];

    // 填充当前的目标
    for (int i = 0; i < tarData.length; i++) {
      tarMap[tarData[i]]++;
    }

    int left = 0;
    int right = 0;

    // 窗口中异位字符的统计
    int slideCount = 0;

    while (right < srcData.length) {
      char rightItem = srcData[right];

      // 跳过无用字符
      if (tarMap[rightItem] == 0) {
        right++;
        continue;
      }

      // 针对存在重复的情况，只统计窗口允许的数量以内的
      if (slideMap[rightItem] < tarMap[rightItem]) {
        slideCount++;
      }

      slideMap[rightItem]++;
      right++;

      // 如果目标被找到
      while (slideCount == tarData.length) {

        if (right - left == tarData.length) {
          resultList.add(left);
        }

        char leftItem = srcData[left];

        // 跳过无用字符
        if (tarMap[leftItem] == 0) {
          left++;
          continue;
        }

        // 当窗口内的数量与目标一致时，则说明当前满足，由于需要滑动，需要减去一个有效位
        if (slideMap[leftItem] == tarMap[leftItem]) {
          slideCount--;
        }

        slideMap[leftItem]--;
        left++;
      }
    }

    return resultList;
  }
}
