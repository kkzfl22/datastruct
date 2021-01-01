package com.liujun.datastruct.base.search.binarysearch2.leetcode162;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试峰值
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
    int[] nums = {1, 2, 3, 1};
    this.findPeakElements(nums, 2);
  }

  @Test
  public void testSolution3() {
    int[] nums = {2,1};
    this.findPeakElements(nums, 0);
  }


  @Test
  public void testSolution4() {
    int[] nums = {1,2};
    this.findPeakElements(nums, 1);
  }

  @Test
  public void testSolution2() {
    int[] nums = {1, 2, 1, 3, 5, 6, 4};
    int firstRsp = instance.findPeakElement(nums);
    Assert.assertThat(firstRsp, Matchers.oneOf(2, 5));
  }

  public void findPeakElements(int[] nums, int target) {
    int firstRsp = instance.findPeakElement(nums);
    Assert.assertEquals(target, firstRsp);
  }
}
