package com.liujun.datastruct.algorithm.backtrackingAlgorithm.packageZoneOne;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class TestPkgDemo {

  @Test
  public void testDemo() {
    int[] items = new int[] {10, 20, 30, 40, 35, 45, 55, 75};

    PkgDemo instance = new PkgDemo();
    instance.f(0, 0, items, 5, 110);
    System.out.println("最大重量:" + instance.maxW);
  }
}
