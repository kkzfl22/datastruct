package com.liujun.datastruct.algorithm.backtrackingAlgorithm.packageZoneOne;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/24
 */
public class TestPackage {

  @Test
  public void testpackage() {
    int[] items = new int[] {10, 20, 30, 40, 35, 45, 55, 75};
    // 假如背包只能承受100Kg，物品不能分割，计算
    Package pkg = new Package();

    pkg.countMaxPkg(0, 0, items, 8, 100);
    System.out.println("最大值:" + pkg.maxW);
  }
}
