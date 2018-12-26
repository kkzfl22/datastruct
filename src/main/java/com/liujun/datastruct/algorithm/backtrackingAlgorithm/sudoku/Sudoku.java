package com.liujun.datastruct.algorithm.backtrackingAlgorithm.sudoku;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class Sudoku {
  private static final int MAX_LENGTH = 9;

  public void count(int[][] data, int row, int column) {

    // 结束条件
    if (row == MAX_LENGTH - 1 && column == MAX_LENGTH) {
      print(data);
      return;
    }

    if (column == MAX_LENGTH) {
      row++;
      column = 0;
    }

    // 1,检查当前所定位的位置是否已经有数据
    if (data[row][column] == 0) {
      for (int i = 1; i <= MAX_LENGTH; i++) {
        if (check(data, row, column, i)) {
          data[row][column] = i;
          count(data, row, column + 1);
          // 当遇到路走不通的时候，将行列设置为初始值，重新再来一遍
          data[row][column] = 0;
        }
      }
    }
    // 如果当前已经存在数据，则进行下一列
    else {
      count(data, row, column + 1);
    }
  }

  /**
   * 检查当前行与列设置是否OK
   *
   * @param data 数据信息
   * @return
   */
  private boolean check(int[][] data, int row, int column, int value) {
    // 1,检查该行该列是否重复
    for (int i = 0; i < MAX_LENGTH; i++) {
      if (data[row][i] == value || data[i][column] == value) {
        return false;
      }
    }

    int startRow = row / 3;
    int startColumn = column / 3;
    // 检查整个数独中是否重复
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (data[startRow * 3 + i][startColumn * 3 + j] == value) {
          return false;
        }
      }
    }

    return true;
  }

  public void print(int[][] data) {
    for (int i = 0; i < MAX_LENGTH; i++) {
      for (int j = 0; j < MAX_LENGTH; j++) {
        System.out.print(data[i][j] + "\t");
      }
      System.out.println();
    }
    System.out.println();
  }
}
