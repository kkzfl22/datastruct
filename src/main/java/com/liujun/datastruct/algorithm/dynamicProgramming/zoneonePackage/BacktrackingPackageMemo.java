package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackage;

/**
 * 使用回塑算法来解决0-1背包的问题,
 *
 * <p>添加备忘录的方式
 *
 * <p>1，背包限制了重量
 *
 * <p>2，将放入的物品重量最大化
 *
 * <p>3,限制物品的个数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/26
 */
public class BacktrackingPackageMemo {

  /** 背包的总重量 */
  public int maxWeight = Integer.MIN_VALUE;

  /** 使用备忘录的方式来记录下访问过的，避免重复访问，一级索引为物品的编号，二级记录下当前总和 */
  public boolean[][] memo = new boolean[5][100];

  /**
   * 计算物品能放入的最大总重量
   *
   * @param itemIndex 发前物品索引号
   * @param sumWeight 当前物品的总重量
   * @param items 物品信息
   * @param maxNum 最大物品数
   * @param maxWeightParms 最
   */
  public void countPackage(
      int itemIndex, int sumWeight, int[] items, int maxNum, int maxWeightParms) {
    // 递归的结束条件，当前物品超过最大，或者重量量达到maxWeightParams
    if (itemIndex == maxNum || sumWeight == maxWeightParms) {
      if (sumWeight >= maxWeight) {
        maxWeight = sumWeight;
      }
      return;
    }

    // 如果当前已经被访问过了，则不再重复访问
    if (memo[itemIndex][sumWeight]) {
      return;
    }

    // 标识当前已经被访问
    memo[itemIndex][sumWeight] = true;

    // 针对每个物品有放入与不放入两种情况，情况1，不放入
    countPackage(itemIndex + 1, sumWeight, items, maxNum, maxWeightParms);

    // 如果总重量超过了最大值，则不再被放入
    if (sumWeight + items[itemIndex] <= maxWeightParms) {
      countPackage(itemIndex + 1, sumWeight + items[itemIndex], items, maxNum, maxWeightParms);
    }
  }
}
