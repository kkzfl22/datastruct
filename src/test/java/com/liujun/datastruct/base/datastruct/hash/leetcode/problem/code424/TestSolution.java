package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code424;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试最大替换最大字符串
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  /** 替换最大重复子串 */
  @Test
  public void replaceMax() {
    this.replaceMaxRun("ABAB", 2, 4);
    this.replaceMaxRun("ABABAAA", 2, 7);
    this.replaceMaxRun("ABABAAB", 2, 6);
    this.replaceMaxRun("AABABBA", 1, 4);
    this.replaceMaxRun("AABCABBB", 2, 6);
    this.replaceMaxRun("AABABBA", 1, 4);
    this.replaceMaxRun("ABBB", 2, 4);
  }

  private void replaceMaxRun(String s, int k, int targetNum) {
    SolutionBase instance = new SolutionBase();
    this.installCheck(instance, s, k, targetNum);
    // 实现2的检查
    Solution instance2 = new Solution();
    this.installSolution(instance2, s, k, targetNum);
  }

  private void installCheck(SolutionBase instance, String s, int k, int targetNum) {
    int result = instance.characterReplacement(s, k);
    Assert.assertEquals(result, targetNum);
  }

  private void installSolution(Solution instance, String s, int k, int targetNum) {
    int result = instance.characterReplacement(s, k);
    Assert.assertEquals(result, targetNum);
  }
}
