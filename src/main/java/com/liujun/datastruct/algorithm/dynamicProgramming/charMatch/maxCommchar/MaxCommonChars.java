package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.maxCommchar;

/**
 * 最大公共子串的求解，先使用回塑来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/08
 */
public class MaxCommonChars {

  /** 最大公共子串长度 */
  public int maxCharNumScope = Integer.MIN_VALUE;

  /**
   * 进行最大公共子串的求解
   *
   * @param src 原字符串
   * @param i 字符索引位置
   * @param target 目标字符串
   * @param j 目标字符比对位置
   * @param maxCharNum 最大公共子串长度
   */
  public void recursionCount(char[] src, int i, char[] target, int j, int maxCharNum) {

    // 1,首先写结束条件，那就是
    int srcLength = src.length;
    int targetLength = target.length;

    // 如果字符串到达了末尾，则当前计算结束
    if (i == srcLength || targetLength == j) {

      if (maxCharNumScope < maxCharNum) {
        maxCharNumScope = maxCharNum;
      }

      return;
    }

    // 如果当前字符相同，则公共长度加1，继续递归匹配
    if (src[i] == target[j]) {
      recursionCount(src, i + 1, target, j + 1, maxCharNum + 1);
    }
    // 如果不匹配，存在3种情况，src[i+1]、target[j+1]，（src[i+1]和target[j+1]）继续匹配，但公共长度不加
    else {
      recursionCount(src, i + 1, target, j, maxCharNum);
      recursionCount(src, i, target, j + 1, maxCharNum);
      recursionCount(src, i + 1, target, j + 1, maxCharNum);
    }
  }
}
