package com.liujun.datastruct.base.datastruct.recursion.leetcode.code39.solution;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 进行递归问题的求解操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/20
 */
public class TestSolutionMySelf {

  @Test
  public void testSolutionCount() {
    SolutionMySelf instance = new SolutionMySelf();

    int[] src = {2, 3, 6, 7};
    List<List<Integer>> list = instance.combinationSum(src, 7);
    for (List<Integer> data : list) {
      System.out.println(data);
    }
    Assert.assertEquals(2, list.size());
  }

  @Test
  public void testSolutionCount2() {
    SolutionMySelf instance = new SolutionMySelf();

    int[] src = {2, 3, 5};
    List<List<Integer>> list = instance.combinationSum(src, 8);
    for (List<Integer> data : list) {
      System.out.println(data);
    }

    Assert.assertEquals(3, list.size());
  }

  @Test
  public void testContext() {
    List<Integer[]> value = new ArrayList<>();
    value.add(new Integer[] {1, 2});
    value.add(new Integer[] {1, 2});

    Integer[] context = new Integer[] {1, 2};
    System.out.println(value.contains(context));
  }
}
