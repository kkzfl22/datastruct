package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.levenshtein;

/**
 * 进行字符串的匹配操作，使用莱文斯坦距离进行计算
 *
 * <p>使用动态规划来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/06
 */
public class CharMatchersDynamic {

  /**
   * 进行莱文斯坦距离的计算,使用动态规划来解决
   *
   * @param src 原始字符串
   * @param target 目录字符串
   */
  public int lwstbt(char[] src, char[] target) {

    // 计算两个字符的编辑距离
    int row = src.length;
    int column = target.length;

    int[][] status = new int[row][column];

    // 初始化第0行的编辑距离
    for (int i = 0; i < row; i++) {
      if (src[i] == target[i]) {
        status[0][i] = i;
      } else if (i != 0) {
        status[0][i] = status[0][i - 1] + 1;
      } else {
        status[0][i] = 1;
      }
    }

    // 初始第0列的数
    for (int i = 0; i < column; i++) {
      if (src[i] == target[i]) {
        status[i][0] = i;
      } else if (i != 0) {
        status[i][0] = status[i - 1][0] + 1;
      } else {
        status[i][0] = 1;
      }
    }

    // 开始进行动态规划的状态的计算
    for (int i = 1; i < row; i++) {
      for (int j = 1; j < column; j++) {
        // 当发生现个字符相等时,行列式都加1，编辑距离不加
        if (src[i] == target[j]) {
          status[i][j] = min(status[i - 1][j] + 1, status[i][j - 1] + 1, status[i - 1][j - 1]);
        } else {
          status[i][j] = min(status[i - 1][j] + 1, status[i][j - 1] + 1, status[i - 1][j - 1] + 1);
        }
      }
    }

    return status[row - 1][column - 1];
  }

  private int min(int x, int y, int z) {
    int min = Integer.MAX_VALUE;

    if (x < min) {
      min = x;
    }
    if (y < min) {
      min = y;
    }
    if (z < min) {
      min = z;
    }

    return min;
  }
}
