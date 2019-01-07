package com.liujun.datastruct.algorithm.dynamicProgramming.counttwo;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/04
 */
public class CountTwo {

  public int minDist = Integer.MAX_VALUE;

  /**
   * 求解的最短路径长度
   *
   * <p>使用回塑的思想来解决
   *
   * @param i
   * @param j
   * @param dest
   * @param data
   * @return
   */
  public void count(int i, int j, int dest, int[][] data) {
    int length = data.length - 1;

    // 当检查到终点时，进行检查
    if (i == length && j == length) {

      // 面要添加最后一个节点的值
      dest = dest + data[i][j];

      if (dest < minDist) {
        minDist = dest;
      }

      System.out.println("最后结果：" + minDist + ",data的值:" + data[i][j]);
      return;
    }

    // 进行当前按行的遍历操作
    if (i < length) {
      count(i + 1, j, dest + data[i][j], data);
    }

    if (j < length) {
      count(i, j + 1, dest + data[i][j], data);
    }
  }

  /** 使用一个4*4的矩阵来求解问题 */
  public int[][] remarks = new int[4][4];

  /**
   * 使用递归加备忘录的方式来求解（0，0）至(n-1,n-1)的解
   *
   * @param i 当前的行编号
   * @param j 当前的列编号
   * @return 当前求解的最优值
   */
  public int recursionCount2(int i, int j, int[][] data) {

    if (i == 0 && j == 0) {
      return data[0][0];
    }

    if (remarks[i][j] > 0) {
      return remarks[i][j];
    }

    int leftMin = Integer.MAX_VALUE;
    // 要得到最终解只有两条路，正推为i-1,j，或者i,j-1
    if (j - 1 >= 0) {
      leftMin = recursionCount2(i, j - 1, data);
    }

    int rightMin = Integer.MAX_VALUE;

    if (i - 1 >= 0) {
      rightMin = recursionCount2(i - 1, j, data);
    }

    int currData = data[i][j] + Math.min(leftMin, rightMin);
    remarks[i][j] = currData;

    return currData;
  }

  /**
   * 求解（0，0）到(n-1,n-1)的最短路径
   *
   * @param data 数据
   * @return 最短路径值
   */
  public int countDist(int[][] data) {
    int length = data.length;
    int[][] status = new int[length][length];

    // 初始化动态数据
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        status[i][j] = -1;
      }
    }

    int sum = 0;
    // 初始化每一行的记录
    for (int i = 0; i < length; i++) {
      sum += data[0][i];
      status[0][i] = sum;
    }

    sum = 0;

    // 初始化第一列
    for (int i = 0; i < length; i++) {
      sum += data[i][0];
      status[i][0] = sum;
    }

    // 开始进行动态规划的问题求解
    for (int i = 1; i < length; i++) {
      // 进行当前行的数据求解
      for (int j = 1; j < length; j++) {
        status[i][j] = data[i][j] + Math.min(status[i - 1][j], status[i][j - 1]);
      }
    }

    return status[length - 1][length - 1];
  }
}
