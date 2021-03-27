package com.liujun.datastruct.base.leetcode.slide.code0076;

import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试最小覆盖子串
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMinWindow {

  @Test
  public void testData() {
    this.minWindow("AAABBCDACAB", "AABC", "ACAB");
    this.minWindow("ADOBECODEBANC", "ABC", "BANC");
    this.minWindow("a", "a", "a");
    this.minWindow("a", "aa", "");
    this.minWindow("a", "b", "");
    this.minWindow("aa", "aa", "aa");
    this.minWindow("bbaa", "aba", "baa");
    this.minWindow("abc", "ac", "abc");
    this.minWindow("cabwefgewcwaefgcf", "cae", "cwae");
    this.minWindow(
        FileUtils.readClassPathLittleFile("struct/leetcode/076/big-src.txt"),
        FileUtils.readClassPathLittleFile("struct/leetcode/076/big-target.txt"),
        FileUtils.readClassPathLittleFile("struct/leetcode/076/big-response.txt"));
  }

  public void minWindow(String s, String t, String target) {
    // SolutionInf instance = new Solution();
    // this.assertCheck(instance, s, t, target);

    // SolutionInf instanceOptimize = new SolutionOptimize();
    // this.assertCheck(instanceOptimize, s, t, target);
    //
    SolutionInf instanceOptimize = new SolutionOptimize2();
    this.assertCheck(instanceOptimize, s, t, target);
    //
    // SolutionInf instanceOptimize3 = new SolutionOptimize3();
    // this.assertCheck(instanceOptimize3, s, t, target);
  }

  private void assertCheck(SolutionInf instance, String s, String t, String target) {
    String result = instance.minWindow(s, t);
    Assert.assertEquals(result, target);
  }
}
