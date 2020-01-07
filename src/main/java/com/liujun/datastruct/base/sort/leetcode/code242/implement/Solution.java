package com.liujun.datastruct.base.sort.leetcode.code242.implement;

/**
 * 验证是否为有诳的字母异位词
 *
 * <p>我使用的是排序的解法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/25
 */
public class Solution {

  public boolean isAnagram(String s, String t) {
    if (null == s || t == null) {
      return false;
    }

    // 当长度不财，可直接返回不同
    if (s.length() != t.length()) {
      return false;
    }

    char[] sarraydata1 = s.toCharArray();
    char[] tarraydata = t.toCharArray();
    // 进行字符串的排序操作
    sort(sarraydata1);
    sort(tarraydata);

    boolean rps = true;
    for (int i = 0; i < sarraydata1.length; i++) {
      if (sarraydata1[i] != tarraydata[i]) {
        rps = false;
        break;
      }
    }

    return rps;
  }

  /**
   * 使用插入排序对数据进行排序操作
   *
   * @param src 待排序的数据
   */
  private void sort(char[] src) {
    for (int i = 1; i < src.length; i++) {
      char item = src[i];
      int insertIndex = i;
      // 查找插入点的位置
      for (int j = 0; j < i; j++) {
        if (src[j] > src[i]) {
          insertIndex = j;
          break;
        }
      }
      // 进行数据的搬移操作
      for (int j = i; j > insertIndex; j--) {
        src[j] = src[j - 1];
      }
      // 进行插入点位置数据的放入
      src[insertIndex] = item;
    }
  }
}
