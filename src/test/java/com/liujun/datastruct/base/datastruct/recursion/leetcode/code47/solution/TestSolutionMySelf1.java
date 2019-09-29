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
public class TestSolutionMySelf1 {

  @Test
  public void testCount() {
    SolutionMySelfSwatch instance = new SolutionMySelfSwatch();

    int[] data = new int[] {1, 2, 3};

    List<List<Integer>> list = instance.solution(data);

    for (List<Integer> dataTmp : list) {
      System.out.println(dataTmp);
    }
  }
}
