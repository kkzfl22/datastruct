package com.liujun.datastruct.base.datastruct.recursion.leetcode.code40.implement;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试组合问题的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/26
 */
public class TestSolutionCombinationSum2 {

  /** 实例对象信息 */
  private SolutionCombinationSum2Sort instance = new SolutionCombinationSum2Sort();

  @Test
  public void testcombinationSum2() {
    int[] src = new int[] {10, 1, 2, 7, 6, 1, 5};
    List<List<Integer>> listData = instance.combinationSum2(src, 8);

    System.out.println();

    for (List<Integer> item : listData) {
      System.out.println(item);
    }

    Assert.assertEquals(4, listData.size());
  }

  @Test
  public void testcombinationSumCount() {
    int[] src = new int[] {2, 5, 2, 1, 2};
    List<List<Integer>> listData = instance.combinationSum2(src, 5);

    System.out.println();

    for (List<Integer> item : listData) {
      System.out.println(item);
    }

    Assert.assertEquals(2, listData.size());
  }
}
