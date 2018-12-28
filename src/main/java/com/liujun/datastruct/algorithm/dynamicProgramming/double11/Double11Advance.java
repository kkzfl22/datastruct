package com.liujun.datastruct.algorithm.dynamicProgramming.double11;

/**
 * 双从凑单的问题
 *
 * <p>即购物车中有n件商品
 *
 * <p>现有满200减50的券
 *
 * <p>选择最小的满足于200的商品，尽可能的薅羊毛
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/28
 */
public class Double11Advance {

  /**
   * 淘宝双11的凑单问题
   *
   * @param prices 价格数组
   * @param itemNum 商品数量
   * @param condition 满减的条件
   */
  public void double11(int[] prices, int itemNum, int condition) {

    int maxValue = 3 * condition;

    // 因为实际超过3倍的大小，薅羊毛无意义，所以，只选择最大3倍即可
    boolean[][] status = new boolean[itemNum][maxValue];

    // 使用动态规则开始求解满足的最小值
    // 选择把第一件物品加入或者不加入的两种情况
    status[0][0] = true;
    status[0][prices[0]] = true;

    for (int i = 1; i < itemNum; i++) {
      for (int j = 0; j < maxValue; j++) {
        // 将上一个物品所以达到的状态记录到当前状态中
        if (status[i - 1][j] == true) {
          status[i][j] = true;
        }
      }

      // 加入当前物品的状态
      for (int j = 0; j <= maxValue - prices[i]; j++) {
        // 当前物品所以达到的状态记录到当前状态中
        if (status[i - 1][j] == true) {
          status[i][j + prices[i]] = true;
        }
      }
    }

    // 找到满足的最小值即可当前商品的可购买商品
    int minValue = -1;

    for (minValue = condition; minValue < maxValue; minValue++) {
      if (status[itemNum - 1][minValue] == true) {
        break;
      }
    }
    System.out.println("当前最小值：" + minValue);

    if (maxValue <= -1) {
      return;
    }

    // 打印运行了哪些物品
    for (int i = itemNum - 1; i >= 1; i--) {
      if (minValue - prices[i] >= 0 && status[i - 1][minValue - prices[i]] == true) {
        System.out.println("选择了" + prices[i] + "物品");
        minValue = minValue - prices[i];
      }
    }
    if (minValue != 0) {
      System.out.println("选择了" + prices[0] + "物品");
    }
  }
}
