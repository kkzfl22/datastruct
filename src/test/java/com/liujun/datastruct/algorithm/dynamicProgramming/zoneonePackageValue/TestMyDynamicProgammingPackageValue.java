package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackageValue;

import org.junit.Test;

/**
 * 进行动态的求解价值最大
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/27
 */
public class TestMyDynamicProgammingPackageValue {

  @Test
  public void mydynamicProgammingValue() {
    int[] items = new int[] {2, 2, 4, 6, 3};
    int[] values = new int[] {3, 4, 8, 9, 6};

    int maxNum = 5;
    int maxWeight = 9;

    MyDynamicProgammingPackageValue instance = new MyDynamicProgammingPackageValue();
    int maxValue = instance.dynamicProgammingPackageMaxValue(items, values, maxNum, maxWeight);
    System.out.println("最大总价值为 ：" + maxValue);

    System.out.println();
  }

  /** 使用优先手段使用一维数据来进行求解 */
  @Test
  public void mydynamicProgammingValueMem() {
    int[] items = new int[] {2, 2, 4, 6, 3};
    int[] values = new int[] {3, 4, 8, 9, 6};

    int maxNum = 5;
    int maxWeight = 9;

    MyDynamicProgammingPackageValue instance = new MyDynamicProgammingPackageValue();
    int maxValue = instance.dynamicProgammingPackageMaxValueMem(items, values, maxNum, maxWeight);
    System.out.println("最大总价值为 ：" + maxValue);

    System.out.println();
  }

  //  @Test
  //  public void knapsack3() {
  //    int[] items = new int[] {2, 2, 4, 6, 3};
  //    int[] values = new int[] {3, 4, 8, 9, 6};
  //
  //    int maxNum = 5;
  //    int maxWeight = 9;
  //
  //    int maxValue = MyDynamicProgammingPackageValue.knapsack3(items, values, maxNum, maxWeight);
  //    System.out.println("最大总价值为 ：" + maxValue);
  //  }
}
