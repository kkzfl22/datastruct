package com.liujun.datastruct.base.datastruct.recursion.leetcode.code47.solution;

import org.junit.Test;

import java.util.List;

/**
 * 实例信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/22
 */
public class TestSolutionMySelf {

  @Test
  public void testCount() {
    SolutionMySelf instance = new SolutionMySelf();

    int[] data = new int[] {1, 1, 2};

    List<List<Integer>> list = instance.permuteUnique(data);

    for (List<Integer> dataTmp : list) {
      System.out.println(dataTmp);
    }
  }
}
