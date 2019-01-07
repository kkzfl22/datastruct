package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.levenshtein;

/**
 * 进行字符串的匹配操作，使用莱文斯坦距离进行计算
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/06
 */
public class CharMatchers {

  /** 最小编辑距离 */
  public int minDest = Integer.MAX_VALUE;

  /**
   * 进行莱文斯坦距离的计算
   *
   * @param i 当前源字符串的索引号
   * @param j 目标字符中的索引号
   * @param edit 编辑距离
   * @param src 原始字符串
   * @param target 目录字符串
   */
  public void lwstbt(int i, int j, int edit, char[] src, char[] target) {

    int srcLength = src.length;
    int outLength = target.length;

    if (i == srcLength || j == outLength) {
      // 当前出两个字符长度不一的情况
      if (i < srcLength) {
        edit += srcLength - i;
      }

      if (j < outLength) {
        edit += outLength - j;
      }

      if (minDest > edit) {
        minDest = edit;
      }
      return;
    }

    // 如果两个字符串匹配
    if (src[i] == target[j]) {
      lwstbt(i + 1, j + 1, edit, src, target);
    }
    // 当出现不匹配的情况，则需要进行几种情况操作
    else {
      // 1,情况1，删除src[i]或者在target[j]前添加一个字符
      lwstbt(i + 1, j, edit + 1, src, target);
      // 情况2，删除target[j]或者在src[i]前添加一个字符
      lwstbt(i, j + 1, edit+1, src, target);
      // 情况3，将src[i]与targer[j]替换成相同的字符
      lwstbt(i + 1, j + 1, edit+1, src, target);
    }
  }
}
