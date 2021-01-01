package com.liujun.datastruct.base.search.binarysearch2.leetcode074;

/**
 * 在一个有序的二维数组内进行搜索。
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /**
   * 有序的二维数组搜索。
   *
   * @param matrix 二维数组搜索
   * @param target 目标值
   * @return true 存在 false 不存在
   */
  public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0) {
      return false;
    }

    // 二分分为两级，第一级搜索第一列，搜索最接近于搜索数的值
    int rowIndex = this.getRowIndex(matrix, target);

    if (rowIndex == -1) {
      return false;
    }
    // 第二级搜索，列所在的那行
    int low = 0, mid, high = matrix[rowIndex].length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      if (matrix[rowIndex][mid] == target) {
        return true;
      } else if (matrix[rowIndex][mid] < target) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    return false;
  }

  /**
   * 获取行索引
   *
   * @param matrix 二维数组
   * @param target 目标值
   * @return 查找到的行号索引
   */
  private int getRowIndex(int[][] matrix, int target) {
    int rowIndex = -1;
    int low = 0, mid, high = matrix.length - 1;
    while (low <= high) {
      mid = low + (high - low) / 2;

      // 找最接近于当前值的索引
      if (matrix[mid][0] <= target) {
        if (mid != matrix.length - 1 && matrix[mid + 1][0] > target) {
          rowIndex = mid;
          break;
        }
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    // 当值未找到时，则在末尾对比
    if (rowIndex == -1) {
      if (target <= matrix[matrix.length - 1][matrix[matrix.length - 1].length - 1]) {
        rowIndex = matrix.length - 1;
      }
    }

    return rowIndex;
  }
}
