package com.liujun.datastruct.base.datastruct.charMatch.simple.rk;

import java.util.HashMap;
import java.util.Map;

/**
 * rk算法，BF算法的升级版，
 *
 * <p>加入了hash散列计算，使时间复杂度降低到了O(N)
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class RabinKarp {

  //  private static final char[] RK_STR = {
  //    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
  // 'S',
  //    'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
  // 'l',
  //    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '
  //  };
  private static final char[] RK_STR = {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
    't', 'u', 'v', 'w', 'x', 'y', 'z', ' '
  };

  private static final int SCALE = RK_STR.length;

  /** 字符串对照表 */
  private static final Map<Character, Integer> CHAR_MAP = new HashMap<>(RK_STR.length);

  static {
    for (int i = 0; i < RK_STR.length; i++) {
      CHAR_MAP.put(RK_STR[i], i + 1);
    }
  }

  /**
   * 进行字符串的查找函数
   *
   * @param src 源字符串
   * @param find 查找的字符串
   * @return 返回索引的下标位置
   */
  public int find(String src, String find) {

    if (null == src || null == find) {
      return -1;
    }

    char[] srcCharrs = src.toCharArray();
    char[] findChars = find.toCharArray();

    // 1,计算查找字符串的hash
    long findHash = countHash(findChars);

    int findIndex = 0;
    while (findIndex < srcCharrs.length) {
      // 如果超过了原始字符，则直接 返回
      if (findIndex + findChars.length > src.length()) {
        return -1;
      }

      int end = findIndex + findChars.length;

      long hash2 = countHash2(srcCharrs, findIndex, end);

      if (hash2 == findHash) {
        return findIndex;
      }
      findIndex = findIndex + 1;
    }

    return -1;
  }

  /**
   * 计算hash值2的方法
   *
   * @param countArrs 原始字符串1
   * @param start 起始位置
   * @param end 结束位置
   * @return 查找到索引位置
   */
  private long countHash2(char[] countArrs, int start, int end) {
    long rsp = 0L;

    // 1,进行模式串的计算
    for (int i = end - 1; i >= start; i--) {
      double value = Math.pow(SCALE, CHAR_MAP.get(countArrs[i]));
      rsp += value;
    }

    return rsp;
  }

  /**
   * 计算hash值1的方法
   *
   * @param countArrs
   * @return
   */
  private long countHash(char[] countArrs) {
    long rsp = 0L;

    // 1,进行模式串的计算
    for (int i = countArrs.length - 1; i >= 0; i--) {
      double value = Math.pow(SCALE, CHAR_MAP.get(countArrs[i]));
      rsp += value;
    }

    return rsp;
  }
}
