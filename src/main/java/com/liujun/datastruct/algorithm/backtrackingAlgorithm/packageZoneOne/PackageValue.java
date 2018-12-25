package com.liujun.datastruct.algorithm.backtrackingAlgorithm.packageZoneOne;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用回溯的思想来解决0-1背包的问题
 *
 * <p>每个物品的重量不同，价值也不相同，在重量不超过背包重量的前提下，让背包的总价值最大化
 *
 * <p>解决的主要思路是通过回溯
 *
 * <p>重点，对于每个物品来说，有装入背包与不装入背包两种选择，也就是需要考察每个有加入背包的情况与不加入的情况
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/24
 */
public class PackageValue {

  /** 背包中物品总重量的最大值 */
  public int maxValue = Integer.MIN_VALUE;

  /** 当前背包的最大总重量 */
  public int sumMaxWeight = Integer.MIN_VALUE;

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
   * @param sumValue 当前的总重量
   * @param items 物品
   * @param maxNum 最大的数物品的个数
   * @param maxWeight
   */
  public void countMaxPkg(
      int index, int sumValue, int sumWeight, PkgValue[] items, int maxNum, int maxWeight) {

    // 1,如果当前重量到达最大总重量，或者数量达到最达限制，则设置当前最大值
    if (index == maxNum || sumWeight == maxWeight) {
      // 检查总重量是否更重
      if (sumMaxWeight < sumWeight) {
        sumMaxWeight = sumWeight;
      }
      // 检查当前价值是否更大
      if (maxValue < sumValue) {
        maxValue = sumValue;
      }

      return;
    }

    // 针对每个物品，有当前不加入背包中计算价值
    countMaxPkg(index + 1, sumValue, sumWeight, items, maxNum, maxWeight);

    // 当前的最大总重量还是要小于限制值
    if (sumWeight + items[index].getWeight() <= maxWeight) {

      // 针对每个物品，有当前加入背包计算价值
      countMaxPkg(
          index + 1,
          sumValue + items[index].getValue(),
          sumWeight + items[index].getWeight(),
          items,
          maxNum,
          maxWeight);
    }
  }
}
