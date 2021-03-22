package com.liujun.datastruct.base.leetcode.slide.code1208;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testSolution() {
    this.assertCheck(new Solution(), "abcd", "bcdf", 3, 3);
    this.assertCheck(new Solution(), "abcd", "cdef", 3, 1);
    this.assertCheck(new Solution(), "abcd", "acde", 0, 1);
    this.assertCheck(new Solution(), "pxezla", "loewbi", 25, 4);
    this.assertCheck(new Solution(), "krrgw", "zjxss", 19, 2);
  }

  private void assertCheck(SolutionInf solution, String s, String t, int maxCost, int target) {
    int result = solution.equalSubstring(s, t, maxCost);
    Assert.assertEquals(result, target);
  }
}
