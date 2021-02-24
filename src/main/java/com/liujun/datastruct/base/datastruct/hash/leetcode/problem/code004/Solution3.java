package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code004;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution3 {

  /**
   * 判断它们是否是同构的
   *
   * @param s
   * @param t
   * @return
   */
  public boolean isIsomorphic(String s, String t) {

    if (null == s || null == t) {
      return false;
    }

    if (s.length() != t.length()) {
      return false;
    }

    Map<Integer, Integer> dataMap = new HashMap<>(s.length());

    char[] srcArray = s.toCharArray();
    char[] targetArray = t.toCharArray();

    for (int i = 0; i < srcArray.length; i++) {
      // 如果数据包括
      int srcIndex = srcArray[i];
      int tarIndex = targetArray[i];
      if (dataMap.containsKey(srcIndex)) {
        if (!dataMap.get(srcIndex).equals(tarIndex)) {
          return false;
        }
      }
      // 如果数据不包括
      else {
        // 检查内容是否已经包括当前目标值
        if (dataMap.containsValue(tarIndex)) {
          return false;
        }

        dataMap.put(srcIndex, tarIndex);
      }
    }

    return true;
  }
}
