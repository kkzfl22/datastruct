package com.liujun.algorithm.greedyAlgorithm.huffman;

import java.util.HashMap;
import java.util.Map;

/**
 * 进行字符的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/18
 */
public class StrProc {

  /**
   * 对字符进行统计操作
   *
   * @param str
   * @return
   */
  public static Map<Character, Integer> countCharset(String str) {

    if (null != str && str.length() > 0) {

      Map<Character, Integer> result = new HashMap<>();

      char[] strChars = str.toCharArray();

      Integer value = null;
      for (int i = 0; i < strChars.length; i++) {
        value = result.get(strChars[i]);

        if (value == null) {
          result.put(strChars[i], 1);
        } else {
          result.put(strChars[i], value + 1);
        }
      }

      return result;
    }
    return null;
  }
}
