package com.liujun.datastruct.base.datastruct.recursion.leetcode.code46.implement;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/25
 */
public class TestSolutionPermute {

  @Test
  public void testPermute() {
    SolutionPermute instance = new SolutionPermute();
    List<List<Integer>> list = instance.permute(new int[] {1, 2, 3});

    for (List<Integer> currData : list) {
      System.out.println(currData);
    }
    Assert.assertEquals(6, list.size());
  }
}
