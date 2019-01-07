package com.liujun.datastruct.algorithm.dynamicProgramming.moneyProblem;

/**
 * 求银币找零的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/02
 */
public class MoneyProblem {

  private int min = -1;
  /**
   * 使用递归求解硬币找零的问题
   *
   * @param monItems
   * @param sumValue
   * @param num
   * @param maxMoney
   * @return
   */
  public int recursionMoney(int[] monItems, int sumValue, int num, int maxMoney) {
    if (sumValue == maxMoney) {
      return num;
    }

    for (int i = 0; i < monItems.length; i++) {
      if (sumValue + monItems[i] <= maxMoney) {
        int couMin = recursionMoney(monItems, monItems[i] + sumValue, num + 1, maxMoney);

        if (min == -1) {
          min = couMin;
        } else {
          if (min > couMin) {
            min = couMin;
          }
        }
      }
    }

    return min;
  }
}
