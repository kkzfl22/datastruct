package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code004;

import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

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

    Map<Character, Character> srcToTarLink = new HashMap<>(s.length());
    Map<Character, Character> tarToSrcLink = new HashMap<>(s.length());

    char[] srcArray = s.toCharArray();
    char[] targetArray = t.toCharArray();

    for (int i = 0; i < srcArray.length; i++) {
      Character targetChar = srcToTarLink.get(srcArray[i]);
      Character srcToChar = tarToSrcLink.get(targetArray[i]);

      if (null == targetChar && srcToChar == null) {
        srcToTarLink.put(srcArray[i], targetArray[i]);
        tarToSrcLink.put(targetArray[i], srcArray[i]);
        continue;
      }


      // 如果影射关系一致说明是对的
      if (null != targetChar && srcToChar != null && targetChar.equals(targetArray[i]) && srcToChar.equals(srcArray[i])) {
        continue;
      } else {
        return false;
      }
    }

    return true;
  }
}
