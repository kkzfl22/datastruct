package com.liujun.datastruct.base.search.binarysearch2.leetcode374;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private GuessGame instance = new Solution();

  @Test
  public void testSolution1() {
    this.assertValue(10, 6);
    this.assertValue(1, 1);
    this.assertValue(2, 1);
    this.assertValue(2, 2);
    this.assertValue(99999999, 1123);
  }

  private void assertValue(int n, int target) {
    instance.targetValue(target);
    int result = instance.guessNumber(n);
    Assert.assertEquals(result, target);
  }
}
