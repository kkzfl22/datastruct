package com.liujun.datastruct.algorithm.dynamicProgramming.counttwo;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/04
 */
public class TestCountTwo {

  @Test
  public void testCount() {
    int[][] data = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};

    CountTwo instance = new CountTwo();
    instance.count(0, 0, 0, data);
    System.out.println("最短路径值" + instance.minDist);
  }

  @Test
  public void testCount2() {
    int[][] data = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};

    CountTwo instance = new CountTwo();
    int value = instance.recursionCount2(3, 3, data);
    System.out.println("最短路径值" + value);
  }

  @Test
  public void testCountDist() {
    int[][] data = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};

    CountTwo instance = new CountTwo();
    int value = instance.countDist(data);
    System.out.println("最短路径值" + value);
  }
}
