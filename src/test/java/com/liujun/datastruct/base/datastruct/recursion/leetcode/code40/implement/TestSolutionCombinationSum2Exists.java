package com.liujun.datastruct.base.datastruct.recursion.leetcode.code40.implement;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 进行使用数组记录方式的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/26
 */
public class TestSolutionCombinationSum2Exists {

  private SolutionCombinationSum2Exists instance = new SolutionCombinationSum2Exists();

  @Test
  public void testcombinationSum2() {

    int[] src = new int[] {10, 1, 2, 7, 6, 1, 5};

    List<List<Integer>> list = instance.combinationSum2(src, 8);

    System.out.println();
    System.out.println();

    for (List<Integer> item : list) {
      System.out.println(item);
    }

    Assert.assertEquals(4, list.size());
  }

  @Test
  public void testcombination3() {
    int[] src = new int[] {2, 5, 2, 1, 2};
    List<List<Integer>> listData = instance.combinationSum2(src, 5);

    System.out.println();
    System.out.println();

    for (List<Integer> item : listData) {
      System.out.println(item);
    }

    Assert.assertEquals(2, listData.size());
  }

  @Test
  public void testcombination4() {
    int[] src = new int[] {1};
    List<List<Integer>> listData = instance.combinationSum2(src, 1);

    System.out.println();
    System.out.println();

    for (List<Integer> item : listData) {
      System.out.println(item);
    }

    Assert.assertEquals(1, listData.size());
  }

  @Test
  public void testcombination5() {
    int[] src = new int[] {1, 1};
    List<List<Integer>> listData = instance.combinationSum2(src, 2);

    System.out.println();
    System.out.println();

    for (List<Integer> item : listData) {
      System.out.println(item);
    }

    Assert.assertEquals(1, listData.size());
  }
}
