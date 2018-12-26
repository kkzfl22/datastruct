package com.liujun.datastruct.algorithm.dynamicProgramming.zoneonePackage;

import org.junit.Test;

/**
 * 进行回溯的测试操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/26
 */
public class TestBacktrackingPackage {

  @Test
  public void backTracking() {
    BacktrackingPackage instance = new BacktrackingPackage();
    /** 背包中的物品中的信息 */
    int[] items = new int[] {10, 20, 30, 50, 40};

    instance.countPackage(0, 0, items, 5, 75);
    System.out.println("最大总重量:" + instance.maxWeight);
  }
}
