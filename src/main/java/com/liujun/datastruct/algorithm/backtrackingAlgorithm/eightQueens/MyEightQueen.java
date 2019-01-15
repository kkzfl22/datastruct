package com.liujun.datastruct.algorithm.backtrackingAlgorithm.eightQueens;

/**
 * 进行八皇后的问题求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/24
 */
public class MyEightQueen {

  /** 八皇后问题的数 */
  private static final int QUEEN_SIZE = 8;

  /** 用来进行队列结果的存储 */
  private int[] result = new int[QUEEN_SIZE];

  /**
   * 八皇后问题求解
   *
   * @param row 行号
   */
  public void call8Queens(int row) {

    // 如果当前已经到行的末尾，则打印当前的结果
    if (row == QUEEN_SIZE) {
      print(result);
      return;
    }

    // 进行当前列的遍历
    for (int column = 0; column < QUEEN_SIZE; column++) {
      // 检查当前是否满足要求,如果满足，则设置result，并进行下一轮的遍历
      if (isOK(row, column)) {
        result[row] = column;
        call8Queens(row + 1);
      }
    }
  }

  /**
   * 检查当前行列是否满足要求
   *
   * @param row 行信息
   * @param column 列信息
   * @return true 满足要求 false 不满足要求
   */
  private boolean isOK(int row, int column) {

    int leftup = column - 1;
    int rightup = column + 1;
    // 按行逐行向上进行遍历
    for (int i = row - 1; i >= 0; i--) {
      // 1，检查当前行是否已经设置
      if (result[i] == column) {
        return false;
      }
      // 2，检查左上部分是否被放置了棋子
      if (leftup >= 0 && result[i] == leftup) {
        return false;
      }
      // 进行右上部分的检查是否被放置了棋子
      if (rightup < QUEEN_SIZE && result[i] == rightup) {
        return false;
      }
      // 左上部分继续向左上
      leftup--;
      rightup++;
    }

    return true;
  }

  /**
   * 打印当前匹配的结果
   *
   * @param result
   */
  private void print(int[] result) {
    for (int row = 0; row < QUEEN_SIZE; row++) {
      for (int column = 0; column < QUEEN_SIZE; column++) {
        if (result[row] == column) {
          System.out.print("Q ");
        } else {
          System.out.print("* ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }
}
