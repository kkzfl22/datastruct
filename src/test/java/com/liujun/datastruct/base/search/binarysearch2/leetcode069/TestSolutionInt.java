package com.liujun.datastruct.base.search.binarysearch2.leetcode069;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolutionInt {

  @Test
  public void testSolution() {
    SolutionInt instance = new SolutionInt();
    int ourRsp = instance.mySqrt(8);
    System.out.println(ourRsp);
    Assert.assertEquals(2, ourRsp);
  }

  @Test
  public void testSolution2() {
    SolutionInt instance = new SolutionInt();
    int ourRsp = instance.mySqrt(9);
    System.out.println(ourRsp);
    Assert.assertEquals(3, ourRsp);
  }

  @Test
  public void testSolution3() {
    SolutionInt instance = new SolutionInt();
    int ourRsp = instance.mySqrt(4);
    System.out.println(ourRsp);
    Assert.assertEquals(2, ourRsp);
  }
}
