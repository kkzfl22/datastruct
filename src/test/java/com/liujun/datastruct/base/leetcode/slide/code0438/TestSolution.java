package com.liujun.datastruct.base.leetcode.slide.code0438;

import com.liujun.utils.AssertCheckUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 测试字母异位词
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testSolution() {
    Solution instance = new Solution();
    this.assertCheck(instance, "cbaebabacd", "abc", Arrays.asList(0, 6));
    this.assertCheck(instance, "abab", "ab", Arrays.asList(0, 1, 2));
  }

  private void assertCheck(Solution instance, String s, String p, List<Integer> target) {
    List<Integer> resultList = instance.findAnagrams(s, p);
    AssertCheckUtils.INSTANCE.assertList(resultList, target);
  }
}
