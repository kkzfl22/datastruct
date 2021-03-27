package com.liujun.datastruct.base.leetcode.slide.code0567;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试567. 字符串的排列
 *
 * <p>permutation
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testPermutation() {
    this.assertCheck("ab", "eidbaooo", true);
    this.assertCheck("ab", "eidboaoo", false);
    this.assertCheck("a", "a", true);
  }

  private void assertCheck(String s1, String s2, boolean result) {
    Solution instance = new Solution();
    boolean rsp = instance.checkInclusion(s1, s2);
    Assert.assertEquals(rsp, result);
  }
}
