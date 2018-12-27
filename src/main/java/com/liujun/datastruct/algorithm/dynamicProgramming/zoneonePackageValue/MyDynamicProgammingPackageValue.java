package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackageValue;

/**
 * 使用动态规则来解决0-1背包求解最大价值的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/27
 */
public class MyDynamicProgammingPackageValue {

  /**
   * 使用动态规则来求物品的最大价值
   *
   * @param weights 物品信息
   * @param values 最大价值
   * @param maxNum 最大数量
   * @param weight 最大重量
   * @return 求最大价值
   */
  public int dynamicProgammingPackageMaxValue(int[] weights, int[] values, int maxNum, int weight) {
    // 用于标识当前层的最大值问题
    int[][] valueStatus = new int[maxNum][weight + 1];

    // 初始化
    for (int i = 0; i < maxNum; i++) {
      for (int j = 0; j <= weight; j++) {
        valueStatus[i][j] = -1;
      }
    }

    // 初始化第一个物品
    valueStatus[0][0] = 0;
    valueStatus[0][weights[0]] = values[0];

    // 使用动态规划来进行求解
    for (int i = 1; i < maxNum; i++) {
      // 求解上一层的背包
      for (int j = 0; j <= weight; j++) {
        // 如果上一层设置了值，在当前物品肯定也会被放入
        if (valueStatus[i - 1][j] >= 0) {
          valueStatus[i][j] = valueStatus[i - 1][j];
        }
      }
      // 求解当前层的问题,背包重量不能超过最大限制
      for (int j = 0; j <= weight - weights[i]; j++) {
        if (valueStatus[i - 1][j] >= 0) {
          // 首先检查当前节点是否已经被设置过值，设置过，则需要比较大小
          int newValue = valueStatus[i - 1][j] + values[i];
          System.out.println("当前的value:" + newValue);
          if (newValue > valueStatus[i][j + weights[i]]) {
            valueStatus[i][j + weights[i]] = newValue;
          }
        }
      }
    }

    int resultValue = -1;

    for (int i = 0; i <= weight; i++) {
      if (valueStatus[maxNum - 1][i] > resultValue) {
        resultValue = valueStatus[maxNum - 1][i];
      }
    }

    return resultValue;
  }

  /**
   * 使用动态规则来求物品的最大价值
   *
   * @param weights 物品信息
   * @param values 最大价值
   * @param maxNum 最大数量
   * @param weight 最大重量
   * @return 求最大价值
   */
  public int dynamicProgammingPackageMaxValueMem(
      int[] weights, int[] values, int maxNum, int weight) {
    // 用于标识当前层的最大值问题
    int[] valueStatus = new int[weight + 1];

    // 初始化
    for (int j = 0; j <= weight; j++) {
      valueStatus[j] = -1;
    }

    // 初始化第一个物品
    valueStatus[0] = 0;
    valueStatus[weights[0]] = values[0];

    // 使用动态规划来进行求解
    for (int i = 1; i < maxNum; i++) {
      // 求解当前层的问题,背包重量不能超过最大限制
      for (int j = weight - weights[i]; j >= 0; j--) {
        if (valueStatus[j] >= 0) {
          // 首先检查当前节点是否已经被设置过值，设置过，则需要比较大小
          int newValue = valueStatus[j] + values[i];
          System.out.println("当前的value:" + newValue);
          if (newValue > valueStatus[j + weights[i]]) {
            valueStatus[j + weights[i]] = newValue;
          }
        }
      }
    }

    int resultValue = -1;

    for (int i = 0; i <= weight; i++) {
      if (valueStatus[i] > resultValue) {
        resultValue = valueStatus[i];
      }
    }

    return resultValue;
  }
}
