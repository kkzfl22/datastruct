package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code349;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution2 {

  @Test
  public void intersection() {
    Solution2 instance = new Solution2();
    Assert.assertEquals(
        2, instance.intersection(new int[] {4, 9, 5}, new int[] {9, 4, 9, 8, 4}).length);
    Assert.assertEquals(1, instance.intersection(new int[] {1, 2, 2, 1}, new int[] {2, 2}).length);
  }
}
