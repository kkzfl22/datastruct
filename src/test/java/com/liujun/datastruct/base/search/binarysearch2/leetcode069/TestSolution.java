package com.liujun.datastruct.base.search.binarysearch2.leetcode069;

import org.junit.Test;

/**
 * 测试求平方根
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testSolution() {
    Solution instance = new Solution();
    double ourRsp = instance.mySqrt(8, 0.0000000001);
    System.out.println(ourRsp);
  }
}
