package com.liujun.datastruct.base.datastruct.recursion.leetcode.code37.implement;

/**
 * 使用递归进行9*9的数独求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/28
 */
public class SudokuSolution {

  /** 空位占用数据 */
  private static final char BASE_DATA = '.';

  /** 数独中可填入的内容 */
  private static final char[] DATA = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

  /** 数独的求解大小 */
  private static final int MAX_LEN = 9;

  /** 小矩阵求解大小 */
  private static final int MIN_LEN = 3;

  public void solveSudoku(char[][] board) {
    this.recurstionCount(board, 0, 0);
  }

  /**
   * 进行递归遍历求解数独中的解
   *
   * @param board 数独中的元素
   * @param row 行号
   * @param column 列号
   * @return true 表示当前求解成功，false 还需要继续求解
   */
  private boolean recurstionCount(char[][] board, int row, int column) {

    // 递归的终止条件
    // 当行满足为最后一行(MAX_LEN-1)，列为最后一列时(MAX_LEN)，则可表示完成
    //为什么行是(MAX_LEN-1),是因为在行加1的条件是列当前为最大值，
    if (row == MAX_LEN - 1 && column == MAX_LEN) {
      return true;
    }

    // 当一行遍历完成行，则需要切换到下一行
    if (column == MAX_LEN) {
      row++;
      column = 0;
    }

    // 首先检查当前位置中是否被使用,如果未被使用，则尝试填入值
    if (board[row][column] == BASE_DATA) {
      // 首先写遍历
      for (int i = 0; i < DATA.length; i++) {
        if (checkValueOk(board, row, column, DATA[i])) {

          // 将其设置到数独的数组中
          board[row][column] = DATA[i];

          // 然后继续按列进行递归操作
          if (recurstionCount(board, row, column + 1)) {
            return true;
          }

          board[row][column] = BASE_DATA;
        }
      }

    } else {
      // 然后继续按列进行递归操作
      if (recurstionCount(board, row, column + 1)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 进行检查行和列的值是否满足
   *
   * @param board 矩阵
   * @param row 行号
   * @param column 列号
   * @param value 值
   * @return 是否满足
   */
  private boolean checkValueOk(char[][] board, int row, int column, char value) {

    // 检查行与列中是否满足
    for (int i = 0; i < MAX_LEN; i++) {
      if (board[row][i] == value || board[i][column] == value) {
        return false;
      }
    }

    // 进行3*3小矩阵检查
    // 取得行的开始索引号
    int startRowIndex = row / MIN_LEN;
    // 取得列的开始索引号
    int startCellIndex = column / MIN_LEN;

    for (int i = 0; i < MIN_LEN; i++) {
      for (int j = 0; j < MIN_LEN; j++) {
        if (board[startRowIndex * MIN_LEN + i][startCellIndex * MIN_LEN + j] == value) {
          return false;
        }
      }
    }

    // 当所有检查都不存在，说明可以满足
    return true;
  }
}
