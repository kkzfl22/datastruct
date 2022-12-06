/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code718;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author liujun
 * @since 2021/6/19
 */
public class TestSolution {

  @Test
  public void testBase() {
    Solution instance = new Solution();
    int max = instance.findLength(new int[] {1, 2, 3, 2, 1}, new int[] {3, 2, 1, 4, 7});
    Assertions.assertEquals(3, max);
  }

  @Test
  public void testBase2() {
    Solution instance = new Solution();
    int max =
        instance.findLength(
            new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0});
    Assertions.assertEquals(3, max);
  }
}
