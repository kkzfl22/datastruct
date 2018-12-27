package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackage;

/**
 * 使用动态规划来解决0-1背包的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/26
 */
public class MyDynamicProgrammingPackage {

  /**
   * 使用动态规则来进行求解背包问题的解
   *
   * @param weight 物品信息
   * @param maxnum 最大数量
   * @param maxWeight 最大重量
   * @return 求得的最大重量
   */
  public int dynamicPargammingPackage(int[] weight, int maxnum, int maxWeight) {

    // 状态数组，用来记录下次背包状的信息
    boolean[][] status = new boolean[maxnum][maxWeight + 1];

    // 初始化第一个物品的信息,针对第一个物品有两种情况，放入背包与不放入背包
    status[0][0] = true;
    status[0][weight[0]] = true;

    // 进行物品的数量遍历
    for (int i = 1; i < maxnum; i++) {
      // 将上一个物品放入到当前物品中
      for (int j = 0; j <= maxWeight; j++) {
        if (status[i - 1][j] == true) {
          status[i][j] = status[i - 1][j];
        }
      }

      // 求得当物品的情况信息,并且需要小于总重量
      for (int j = 0; j <= maxWeight - weight[i]; j++) {
        if (status[i - 1][j]) {
          status[i][j + weight[i]] = true;
        }
      }
    }

    for (int i = maxWeight; i >= 0; i--) {
      if (status[maxnum - 1][i]) {
        return i;
      }
    }

    return 0;
  }

  /**
   * 动态规划求解背包中的问题的最大重量，为减小空间的使用，进行一维数据的改造
   *
   * @param items 物品信息
   * @param maxNum 可以装入背包的最大数量
   * @param maxWeight 最大重量
   * @return 求解的最大重量
   */
  public int dynamicProgammingPackage2(int[] items, int maxNum, int maxWeight) {

    // 状态表示当前物品可达到的状态，
    boolean[] status = new boolean[maxWeight + 1];

    // 初始化数据，可选择将第一个物品装入背包或者其他选择不装入背包
    status[0] = true;
    status[items[0]] = true;

    // 按最大物品来进行遍历
    for (int i = 1; i < maxNum; i++) {
      // 使用倒序循环，以避免重复的计算
      for (int j = maxWeight - items[i]; j >= 0; j--) {
        if (status[j] == true) {
          // 将当前第i个物品装入背包
          status[j + items[i]] = true;
        }
      }
    }

    // 当完成后，进行最大值的遍历
    for (int i = maxWeight; i >= 0; i--) {
      if (status[i] == true) {
        return i;
      }
    }

    return 0;
  }
}
