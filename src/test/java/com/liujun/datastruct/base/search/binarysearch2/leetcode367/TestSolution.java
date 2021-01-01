package com.liujun.datastruct.base.search.binarysearch2.leetcode367;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试有效的完全平方数
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
    this.assertTarget(16, true);
    this.assertTarget(14, false);
    this.assertTarget(9, true);
    this.assertTarget(6, false);
    this.assertTarget(5, false);
  }

  private void assertTarget(int target, boolean value) {
    boolean result = instance.isPerfectSquare(target);
    Assert.assertEquals(value, result);
  }
}
