package com.liujun.datastruct.base.sort.leetcode.code56.implement;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试区间合并
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/07
 */
public class TestSolutionCode562 {

  private SolutionCode562 instance = new SolutionCode562();

  @Test
  public void testMerge() {
    int[][] data = new int[4][2];
    data[0] = new int[] {1, 3};
    data[1] = new int[] {2, 6};
    data[2] = new int[] {8, 9};
    data[3] = new int[] {15, 18};

    int[][] mergeData = instance.merge(data);
    for (int[] dataItem : mergeData) {
      System.out.println(Arrays.toString(dataItem));
    }
  }

  @Test
  public void testMerge2() {
    int[][] data = new int[2][2];
    data[0] = new int[] {1, 4};
    data[1] = new int[] {0, 4};

    int[][] mergeData = instance.merge(data);
    for (int[] dataItem : mergeData) {
      System.out.println(Arrays.toString(dataItem));
    }
  }




  @Test
  public void testMerge3() {
    int[][] data = new int[7][2];
    data[0] = new int[] {2, 3};
    data[1] = new int[] {2, 2};
    data[2] = new int[] {3, 3};
    data[3] = new int[] {1, 3};
    data[4] = new int[] {5, 7};
    data[5] = new int[] {2, 2};
    data[6] = new int[] {4, 6};

    int[][] mergeData = instance.merge(data);
    for (int[] dataItem : mergeData) {
      System.out.println(Arrays.toString(dataItem));
    }
  }

  @Test
  public void testMerge4() {
    int[][] data = new int[2][2];
    data[0] = new int[] {1, 4};
    data[1] = new int[] {4, 5};

    int[][] mergeData = instance.merge(data);
    for (int[] dataItem : mergeData) {
      System.out.println(Arrays.toString(dataItem));
    }
  }
}
