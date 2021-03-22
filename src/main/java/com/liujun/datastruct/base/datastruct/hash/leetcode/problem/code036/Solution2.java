package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code036;

/**
 * 有效的数独
 *
 * <p>利用的的二维数组中每个仅出现一次的特性
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public boolean isValidSudoku(char[][] board) {
    if (board.length < 1) {
      return false;
    }

    // 1. 数字 `1-9` 在每一行只能出现一次。
    // 2. 数字 `1-9` 在每一列只能出现一次。
    // 3. 数字 `1-9` 在每一个以粗实线分隔的 `3x3` 宫内只能出现一次。
    int[][] row = new int[9][9];
    int[][] column = new int[9][9];
    int[][] boxes = new int[9][9];

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (board[i][j] == '.') {
          continue;
        }

        int dataIndex = board[i][j] - '1';
        int boxIndex = (i / 3) * 3 + j / 3;
        if (row[i][dataIndex] != 1
            && column[j][dataIndex] != 1
            && boxes[boxIndex][dataIndex] != 1) {
          row[i][dataIndex] = 1;
          column[j][dataIndex] = 1;
          boxes[boxIndex][dataIndex] = 1;
        } else {
          return false;
        }
      }
    }

    return true;
  }
}
