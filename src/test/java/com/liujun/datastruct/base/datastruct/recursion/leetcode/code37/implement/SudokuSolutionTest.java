package com.liujun.datastruct.base.datastruct.recursion.leetcode.code37.implement;

import org.junit.Test;

/**
 * 解数独的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/28
 */
public class SudokuSolutionTest {

  /** 测试数独的解操作 */
  @Test
  public void testSudoku() {
    SudokuSolution instance = new SudokuSolution();
    char[][] data = {
      {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
      {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
      {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
      {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
      {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
      {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
      {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
      {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
      {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
    };

    instance.solveSudoku(data);

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        System.out.print(data[i][j] + "  ");
      }
      System.out.println();
    }
  }
}
