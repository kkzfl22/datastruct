package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.maxCommchar;

/**
 * 最大公共子串的求解，使用动态规划来解决此问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/08
 */
public class MaxCommonCharsDynamic {

  /**
   * 进行最大公共子串的求解
   *
   * @param src 原字符串
   * @param target 目标字符串
   * @return 最大公共子串
   */
  public int recursionCount(char[] src, char[] target) {

    // 1,首先写结束条件，那就是
    int srcLength = src.length;
    int targetLength = target.length;

    int[][] status = new int[srcLength][targetLength];

    // 初始化当前第一行数据，计算最大公共子串长度
    for (int i = 0; i < srcLength; i++) {
      if (src[0] == target[i]) {
        status[0][i] = 1;
      }
    }

    // 初始化第一列的数据
    for (int i = 0; i < targetLength; i++) {
      if (src[i] == target[0]) {
        status[i][0] = 1;
      }
    }

    // 开始进行动态规划的计算
    for (int i = 1; i < srcLength; i++) {
      for (int j = 1; j < targetLength; j++) {
        // 如果字符匹配，则最大公共长度加1
        if (src[i] == target[j]) {
          status[i][j] = maxNum(status[i - 1][j], status[i][j - 1], status[i - 1][j - 1] + 1);
        }
        // 如果不相同，则最大公共子串长度加不加,保持不变
        else {
          status[i][j] = maxNum(status[i - 1][j], status[i][j - 1], status[i - 1][j - 1]);
        }
      }
    }

    return status[srcLength - 1][targetLength - 1];
  }

  private int maxNum(int x, int y, int z) {
    int result = Integer.MIN_VALUE;

    if (result < x) {
      result = x;
    }
    if (result < y) {
      result = y;
    }
    if (result < z) {
      result = z;
    }
    return result;
  }
}
