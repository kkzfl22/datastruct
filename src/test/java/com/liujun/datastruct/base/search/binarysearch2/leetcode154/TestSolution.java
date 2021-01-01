package com.liujun.datastruct.base.search.binarysearch2.leetcode154;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testFindMin() {
    int[] nums = new int[] {1, 3, 5};
    this.testFindMin(nums, 1);
  }

  @Test
  public void testFindMin2() {
    int[] nums = new int[] {2, 2, 2, 0, 1};
    this.testFindMin(nums, 0);
  }

  @Test
  public void testSolution() {
    int[] nums = {3, 4, 5, 1, 2};
    testFindMin(nums, 1);
  }

  @Test
  public void testSolution2() {
    int[] nums = {4, 5, 6, 7, 0, 1, 2};
    testFindMin(nums, 0);
  }

  @Test
  public void testSolution3() {
    int[] nums = {1};
    testFindMin(nums, 1);
  }

  @Test
  public void testSolution4() {
    int[] nums = {1, 2};
    testFindMin(nums, 1);
  }

  @Test
  public void testSolution5() {
    int[] nums = {2, 1};
    testFindMin(nums, 1);
  }

  @Test
  public void testSolution6() {
    int[] nums = {3,3,1,3};
    testFindMin(nums, 1);
  }

  @Test
  public void testSolution7() {
    int[] nums = {10,1,10,10,10};
    testFindMin(nums, 1);
  }


  private void testFindMin(int[] nums, int target) {
    int rsp = instance.findMin(nums);
    Assert.assertEquals(rsp, target);
  }
}
