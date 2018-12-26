package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackage;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/26
 */
public class TestMyDynamicProgrammingPackage {

  @Test
  public void testknapsack() {
    MyDynamicProgrammingPackage instance = new MyDynamicProgrammingPackage();

    int[] weights = new int[] {2, 2, 4, 6, 3};
    int n = 5;
    int weight = 16;
    // 限制总重为10千克，5个物品以内
    int value = instance.dynamicPargammingPackage(weights, n, weight);
    System.out.println(value);
  }
}
