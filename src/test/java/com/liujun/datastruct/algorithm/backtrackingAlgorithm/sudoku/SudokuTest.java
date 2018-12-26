package com.liujun.datastruct.algorithm.backtrackingAlgorithm.sudoku;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class SudokuTest {

  @Test
  public void testSudoku() {
    int[][] sudoku = {
      {8, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 3, 6, 0, 0, 0, 0, 0},
      {0, 7, 0, 0, 9, 0, 2, 0, 0},
      {0, 5, 0, 0, 0, 7, 0, 0, 0},
      {0, 0, 0, 0, 4, 5, 7, 0, 0},
      {0, 0, 0, 1, 0, 0, 0, 3, 0},
      {0, 0, 1, 0, 0, 0, 0, 6, 8},
      {0, 0, 8, 5, 0, 0, 0, 1, 0},
      {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };

    Sudoku instance = new Sudoku();
    instance.count(sudoku, 0, 0);
    System.out.println("结束:....");

    // instance.print(sudoku);
  }
}
