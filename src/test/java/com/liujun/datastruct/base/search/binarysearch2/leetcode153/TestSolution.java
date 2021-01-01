package com.liujun.datastruct.base.search.binarysearch2.leetcode153;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
    int[] nums = {3, 4, 5, 1, 2};
    runInvoke(nums, 1);
  }

  @Test
  public void testSolution2() {
    int[] nums = {4, 5, 6, 7, 0, 1, 2};
    runInvoke(nums, 0);
  }

  @Test
  public void testSolution3() {
    int[] nums = {1};
    runInvoke(nums, 1);
  }

  @Test
  public void testSolution4() {
    int[] nums = {1, 2};
    runInvoke(nums, 1);
  }

  @Test
  public void testSolution5() {
    int[] nums = {2, 1};
    runInvoke(nums, 1);
  }

  private void runInvoke(int[] nums, int target) {
    int result = instance.findMin(nums);
    Assert.assertEquals(target, result);
  }
}
