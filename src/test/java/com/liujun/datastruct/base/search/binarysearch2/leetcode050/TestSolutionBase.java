package com.liujun.datastruct.base.search.binarysearch2.leetcode050;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试幂等计算
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolutionBase {

  private SolutionBase instance = new SolutionBase();

  @Test
  public void testSolution() {
    this.assertCheck(1.5, 5);
    this.assertCheck(2, 5);
    this.assertCheck(2, -5);
    this.assertCheck(10, -5);
    this.assertCheck(5, 5);
  }

  private void assertCheck(double x, int n) {
    double targetValue = instance.myPow(x, n);
    Assert.assertEquals(Math.pow(x, n), targetValue, 0.2d);
  }
}
