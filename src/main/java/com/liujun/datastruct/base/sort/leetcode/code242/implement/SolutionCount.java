package com.liujun.datastruct.base.sort.leetcode.code242.implement;

/**
 * 验证是否为有诳的字母异位词
 *
 * <p>使用数组来计数解决此统计问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/25
 */
public class SolutionCount {

  private static final char DATA_INIT = 'a';


  public boolean isAnagram(String s, String t) {
    if (null == s || t == null) {
      return false;
    }

    // 当长度不财，可直接返回不同
    if (s.length() != t.length()) {
      return false;
    }

    int[] array = new int[26];
    char[] sarray = s.toCharArray();
    char[] tarray = t.toCharArray();

    for (int i = 0; i < sarray.length; i++) {
      array[sarray[i] - DATA_INIT]++;
    }

    for (int i = 0; i < tarray.length; i++) {
      array[tarray[i] - DATA_INIT]--;
    }
    boolean rspFlag = true;

    for (int i = 0; i < array.length; i++) {
      if (array[i] != 0) {
        rspFlag = false;
        break;
      }
    }

    return rspFlag;
  }
}
