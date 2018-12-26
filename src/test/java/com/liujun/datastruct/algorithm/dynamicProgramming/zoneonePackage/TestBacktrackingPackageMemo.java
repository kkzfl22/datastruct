package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackage;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/26
 */
public class TestBacktrackingPackageMemo {

  @Test
  public void backTracking() {
    BacktrackingPackageMemo instance = new BacktrackingPackageMemo();
    /** 背包中的物品中的信息 */
    int[] items = new int[] {10, 20, 30, 50, 40};

    instance.countPackage(0, 0, items, 5, 85);
    System.out.println("最大总重量:" + instance.maxWeight);
  }
}
