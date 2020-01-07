package com.liujun.datastruct.base.sort.leetcode.code242.implement;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/25
 */
public class TestSolutionCount {

  private SolutionCount instance = new SolutionCount();

  @Test
  public void run() {
    String s = "anagram", t = "nagaram";
    boolean updrsp = instance.isAnagram(s, t);
    Assert.assertEquals(true, updrsp);
  }

  @Test
  public void runFail() {
    String s = "anagramt", t = "nagaram";
    boolean updrsp = instance.isAnagram(s, t);
    Assert.assertEquals(false, updrsp);
  }

  @Test
  public void runSuccess() {
    String s = "eebbbaa", t = "bbbaaee";
    boolean updrsp = instance.isAnagram(s, t);
    Assert.assertEquals(true, updrsp);
  }

  @Test
  public void runFail3() {
    String s = "a", t = "b";
    boolean updrsp = instance.isAnagram(s, t);
    Assert.assertEquals(false, updrsp);
  }
}
