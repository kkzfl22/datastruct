package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code004;

/**
 * 使用数组的效果还是最快
 *
 * <p>执行结果： 通过 显示详情 执行用时： 1 ms ,
 *
 * <p>在所有 Java 提交中击败了 100.00% 的用户 内存消耗： 38.7 MB ,
 *
 * <p>在所有 Java 提交中击败了 14.18% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

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

    // 关联关系的map
    int[] dataMap = new int[256];
    for (int i = 0; i < 256; i++) {
      dataMap[i] = -1;
    }

    char[] srcArray = s.toCharArray();
    char[] targetArray = t.toCharArray();

    for (int i = 0; i < srcArray.length; i++) {
      // 当字符被影射过后，则检查是否相同
      if (dataMap[srcArray[i]] != -1) {
        if (dataMap[srcArray[i]] != targetArray[i]) {
          return false;
        }
        // 能对比成功，则流程继续
        else {
          continue;
        }
      }
      // 当前没有影射关系，则建立关系
      else {
        // 1,首先检查是否已经有关联建立
        for (int j = 0; j < 256; j++) {
          if (dataMap[j] == targetArray[i]) {
            return false;
          }
        }

        // 没有关系，则建立关系
        dataMap[srcArray[i]] = targetArray[i];
      }
    }

    return true;
  }
}
