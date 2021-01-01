package com.liujun.datastruct.base.search.binarysearch2.leetcode074;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试求解二维数组中是否存在目标值
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
    int[][] data = new int[3][4];
    data[0] = new int[] {1, 3, 5, 7};
    data[1] = new int[] {10, 11, 16, 20};
    data[2] = new int[] {23, 30, 34, 50};
    boolean target = instance.searchMatrix(data, 3);
    Assert.assertEquals(true, target);
  }

  @Test
  public void testSolution1() {
    int[][] data = new int[3][4];
    data[0] = new int[] {1, 3, 5, 7};
    data[1] = new int[] {10, 11, 16, 20};
    data[2] = new int[] {23, 30, 34, 50};
    boolean target = instance.searchMatrix(data, 13);
    Assert.assertEquals(false, target);
  }

  @Test
  public void testSolution2() {
    int[][] data = new int[3][4];
    data[0] = new int[] {1, 3, 5, 7};
    data[1] = new int[] {10, 11, 16, 20};
    data[2] = new int[] {23, 30, 34, 50};
    boolean target = instance.searchMatrix(data, 1);
    Assert.assertEquals(true, target);
  }

  @Test
  public void testSolution3() {
    int[][] data = new int[3][4];
    data[0] = new int[] {1, 3, 5, 7};
    data[1] = new int[] {10, 11, 16, 20};
    data[2] = new int[] {23, 30, 34, 50};
    boolean target = instance.searchMatrix(data, 50);
    Assert.assertEquals(true, target);
  }
}
