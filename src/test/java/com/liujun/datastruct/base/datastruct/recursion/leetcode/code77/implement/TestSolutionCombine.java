package com.liujun.datastruct.base.datastruct.recursion.leetcode.code77.implement;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试最大数的组合
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/27
 */
public class TestSolutionCombine {

  private SolutionCombine instance = new SolutionCombine();

  @Test
  public void testCombine() {
    int n = 4;
    int k = 2;

    List<List<Integer>> list = instance.combine(n, k);

    System.out.println();
    System.out.println();

    for (List<Integer> item : list) {
      System.out.println(item);
    }

    Assert.assertEquals(6, list.size());
  }
}
