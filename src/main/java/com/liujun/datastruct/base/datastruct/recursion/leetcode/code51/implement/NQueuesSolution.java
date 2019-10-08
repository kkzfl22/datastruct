package com.liujun.datastruct.base.datastruct.recursion.leetcode.code51.implement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 进行N皇后问题的求解
 *
 * <p>皇后问题的判断 条件有3个
 *
 * <p>1,在一个横排中只有存在一个皇后
 *
 * <p>2，在一个竖排中只能存在一个皇后
 *
 * <p>3，在左对角线中只能存在一个皇后
 *
 * <p>4，在右对角线中只能存在一个皇后
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/29
 */
public class NQueuesSolution {

  /** 字符表示皇后与空位 */
  private static final String[] QUEUES = {"Q", "."};

  public List<List<String>> solveNQueens(int n) {

    List<List<String>> result = new ArrayList<>();

    String[][] data = new String[n][n];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length; j++) {
        data[i][j] = QUEUES[1];
      }
    }

    queueRecurstion(n, 0, data, result);

    // System.out.println("结束后的打印:");
    // print(data);

    return result;
  }

  private void queueRecurstion(int queueMax, int row, String[][] data, List<List<String>> result) {

    if (row == queueMax) {
      // System.out.println("------------------------------");
      // print(data);
      for (String[] dataItem : data) {
        result.add(new ArrayList<>(Arrays.asList(dataItem)));
      }
      return;
      // return true;
    }

    // 按行迭代操作
    for (int i = 0; i < queueMax; i++) {
      // 针对每一个格存在两种情况，放入皇后或者放入空位符
      if (queueOk(data, row, i)) {
        data[row][i] = QUEUES[0];
        queueRecurstion(queueMax, row + 1, data, result);

        data[row][i] = QUEUES[1];
      }
    }

    // return false;
  }

  private void print(String[][] data) {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        System.out.print(data[i][j] + "  ");
      }
      System.out.println();
    }
  }

  /**
   * 检查位置是否可以放入皇后
   *
   * @param data 二维数组表示
   * @param row 行号
   * @param column 列号
   * @return true 表示可以放入 false表示不能放入皇后
   */
  private boolean queueOk(String[][] data, int row, int column) {
    // 检查横排与纵排
    for (int i = 0; i < data.length; i++) {
      if ((null != data[row][i] && data[row][i].equals(QUEUES[0]))
          || (null != data[i][column] && data[i][column].equals(QUEUES[0]))) {
        return false;
      }
    }

    // 检查左上对角线
    int lineColumnStart = column - row;
    int lineRowStart = 0;

    for (int i = 0; i < data.length; i++) {
      // 进行范围的检查
      if ((0 <= lineRowStart + i && lineRowStart + i < data.length)
          && (0 <= lineColumnStart + i && lineColumnStart + i < data.length)) {
        // 检查在大对角钱中是否存在此值
        // System.out.println("左对角坐标:("+);
        if (data[lineRowStart + i][lineColumnStart + i] != null
            && data[lineRowStart + i][lineColumnStart + i].equals(QUEUES[0])) {
          return false;
        }
      }
    }

    // 检查右上对角线
    int rightRowStart = 0;
    int rightColumnStart = column + row;
    for (int i = rightRowStart; i < rightColumnStart; i++) {
      // 检查值的范围
      if ((0 <= rightRowStart + i && rightRowStart + i < data.length)
          && (0 <= rightColumnStart - i && rightColumnStart - i < data.length))
        if (data[rightRowStart + i][rightColumnStart - i] != null
            && data[rightRowStart + i][rightColumnStart - i].equals(QUEUES[0])) {
          return false;
        }
    }

    return true;
  }
}
