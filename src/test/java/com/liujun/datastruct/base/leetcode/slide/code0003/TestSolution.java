package com.liujun.datastruct.base.leetcode.slide.code0003;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试解决方案
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testSolution() {
    this.assertCheck("abcabcbb", 3);
    this.assertCheck("bbbbb", 1);
    this.assertCheck("pwwkew", 3);
    this.assertCheck("", 0);
    this.assertCheck(" ", 1);
    this.assertCheck("au", 2);
  }

  private void assertCheck(String data, int target) {
    SolutionInf instance = new Solution();
    int result = instance.lengthOfLongestSubstring(data);
    Assert.assertEquals(result, target);

    SolutionInf instance2 = new SolutionOptimize();
    int result2 = instance2.lengthOfLongestSubstring(data);
    Assert.assertEquals(result2, target);
  }
}
