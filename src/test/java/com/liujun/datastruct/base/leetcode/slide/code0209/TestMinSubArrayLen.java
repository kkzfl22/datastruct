package com.liujun.datastruct.base.leetcode.slide.code0209;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试最小
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMinSubArrayLen {

  @Test
  public void minSubArrayLen() {
    this.check(7, new int[] {2, 3, 1, 2, 4, 3}, 2);
    this.check(4, new int[] {1, 4, 4}, 1);
    this.check(11, new int[] {1, 1, 1, 1, 1, 1, 1, 1}, 0);
  }

  private void check(int target, int[] nums, int targetValue) {
    SolutionInf install = new Solution();
    this.assertCheck(install, target, nums, targetValue);
  }

  private void assertCheck(SolutionInf install, int target, int[] nums, int targetValue) {
    int result = install.minSubArrayLen(target, nums);
    Assert.assertEquals(targetValue, result);
  }
}
