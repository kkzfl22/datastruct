package com.liujun.datastruct.algorithm.dynamicProgramming.triangle;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/28
 */
public class Triangle {

  /**
   * 求出杨晖三角的最短路径
   *
   * @param triangles
   */
  public void count(int[][] triangles) {
    // 记录下杨晖三角的状态
    int[][] status = new int[triangles.length][triangles[triangles.length - 1].length];

    int startPoint = triangles.length - 1;
    int maxpoint = triangles[triangles.length - 1].length;

    // 初始化相关的数据
    for (int i = 0; i <= startPoint; i++) {
      for (int j = 0; j < maxpoint; j++) {
        status[i][j] = -1;
      }
    }

    // 初始化杨晖三解的第一个顶点
    status[0][startPoint] = triangles[0][startPoint];

    // 开始求解第二个三角形顶点
    // 按层级遍历
    for (int i = 1; i <= startPoint; i++) {
      // 加入当前的位置节点
      int currIndex = 0;
      while (currIndex < maxpoint) {
        if (status[i - 1][currIndex] > 0) {
          // 计算左节点
          int leftValue = status[i - 1][currIndex] + triangles[i][currIndex - 1];

          // 1,检查当前左节点是否已经设置，如果没有，则直接设置
          if (status[i][currIndex - 1] == -1) {
            status[i][currIndex - 1] = leftValue;
          } else {
            if (leftValue < status[i][currIndex - 1]) {
              status[i][currIndex - 1] = leftValue;
            }
          }
          // 计算右节点
          int rightValue = status[i - 1][currIndex] + triangles[i][currIndex + 1];

          if (status[i][currIndex + 1] == -1) {
            status[i][currIndex + 1] = rightValue;
          }
          currIndex++;
        }
        currIndex++;
      }
    }

    int minValue = Integer.MAX_VALUE;
    for (int i = 0; i < maxpoint; i++) {
      if (minValue > status[startPoint][i] && status[startPoint][i] != -1) {
        minValue = status[startPoint][i];
      }
    }
    System.out.println("最短路径结果为:" + minValue);

    int outValue = minValue;
    // 进行倒推出节点
    for (int i = startPoint; i >= 0; i--) {
      // 加入当前的位置节点
      int currIndex = 0;
      while (currIndex < maxpoint) {
        if (status[i][currIndex] == outValue) {
          outValue = outValue - triangles[i][currIndex];
          System.out.print("--> " + triangles[i][currIndex] + " \t");
        }

        currIndex++;
      }
    }
  }
}
