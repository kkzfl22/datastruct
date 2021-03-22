package com.liujun.datastruct.base.leetcode.slide.code1493;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void check() {
    this.assertCheck(new int[] {1, 1, 0, 1}, 3);
    this.assertCheck(new int[] {0, 1, 1, 1, 0, 1, 1, 0, 1}, 5);
    this.assertCheck(new int[] {1, 1, 1}, 2);
    this.assertCheck(new int[] {1, 1, 0, 0, 1, 1, 1, 0, 1}, 4);
    this.assertCheck(new int[] {0, 0, 0}, 0);
  }

  public void assertCheck(int[] nums, int target) {
    Solution instance = new Solution();
    int result = instance.longestSubarray(nums);
    Assert.assertEquals(result, target);
  }
}
