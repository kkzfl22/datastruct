package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackageValue;

import org.junit.Test;

/**
 * 测试回塑的代码求解0-1背包问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/27
 */
public class TestBacktrackingPackageValue {

  @Test
  public void testCountMaxValue() {
    int[] items = new int[] {2, 2, 4, 6, 3};
    int[] values = new int[] {3, 4, 8, 9, 6};

    int maxNum = 5;
    int maxWeight = 9;

    BacktrackingPackageValue instance = new BacktrackingPackageValue();
    instance.countMaxValue(items, values, 0, 0, 0, maxNum, maxWeight);

    System.out.println("max weight:" + instance.sumMaxWeight);
    System.out.println("max value:" + instance.sumMaxValue);
  }
}
