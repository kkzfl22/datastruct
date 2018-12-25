package com.liujun.datastruct.algorithm.backtrackingAlgorithm.packageZoneOne;

/**
 * 使用回溯的思想来解决0-1背包的问题
 *
 * <p>解决的主要思路是通过回溯
 *
 * <p>重点，对于每个物品来说，有装入背包与不装入背包两种选择，也就是需要考察每个有加入背包的情况与不加入的情况
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/24
 */
public class Package {

  /** 背包中物品总重量的最大值 */
  public int maxW = Integer.MIN_VALUE;

  /**
   * 计算最大放入的信息
   *
   * <p>1，物品不能分隔
   *
   * <p>2，在包中放入的数量不能超过maxNum
   *
   * <p>3，包装的总重量不能超过maxWeight
   *
   * @param index 当前物品索引
   * @param sum 当前的总重量
   * @param items 物品
   * @param maxNum 最大的数物品的个数
   * @param maxWeight
   */
  public void countMaxPkg(int index, int sum, int[] items, int maxNum, int maxWeight) {
    // 1,如果当前重量到达最大总重量，或者数量达到最达限制，则设置当前最大值
    if (index == maxNum || sum == maxWeight) {
      // 检查当前是否已经超过了总上一个值
      if (maxW < sum) {
        maxW = sum;
      }

      return;
    }

    countMaxPkg(index + 1, sum, items, maxNum, maxWeight);

    //    // 如果当前还未超过最大值，则继续循环
    if (sum + items[index] <= maxWeight) {
      countMaxPkg(index + 1, sum + items[index], items, maxNum, maxWeight);
    }
  }
}
