package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackageValue;

/**
 * 使用回塑的代码来求解0-1背包问题，在限制最大重量的前提下，让总价值最大
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/27
 */
public class BacktrackingPackageValue {

  /** 当前物品的最大重量 */
  public int sumMaxWeight = Integer.MIN_VALUE;

  /** 当前物品的最大价值 */
  public int sumMaxValue = Integer.MIN_VALUE;

  /**
   * 在背包中装物品，在限制重量的前提下，求最大值价值
   *
   * @param items 物品信息
   * @param values 价值信息
   * @param itemIndex 当前第几个物品的索引号
   * @param sumWeight 重量总和
   * @param sumValue 价值总和
   * @param maxNum 最大物品数
   * @param maxWeight 最大重量限制
   */
  public void countMaxValue(
      int[] items,
      int[] values,
      int itemIndex,
      int sumWeight,
      int sumValue,
      int maxNum,
      int maxWeight) {

    // 如果当前物品已经达到物品数，或者重点已经达到，则计算
    if (itemIndex == maxNum - 1 || sumWeight == maxWeight) {
      if (sumWeight > sumMaxWeight) {
        sumMaxWeight = sumWeight;
      }
      if (sumValue > sumMaxValue) {
        sumMaxValue = sumValue;
      }
      return;
    }

    // 镇对每个物品，有两种情况，
    // 1,将当前物品不放入背包
    countMaxValue(items, values, itemIndex + 1, sumWeight, sumValue, maxNum, maxWeight);
    // 2,将当前物品放入背包
    if (sumWeight + items[itemIndex] < maxWeight) {
      countMaxValue(
          items,
          values,
          itemIndex + 1,
          sumWeight + items[itemIndex],
          sumValue + values[itemIndex],
          maxNum,
          maxWeight);
    }
  }
}
