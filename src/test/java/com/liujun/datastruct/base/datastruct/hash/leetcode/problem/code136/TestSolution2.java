package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code136;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试查找只出现一次的数字
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution2 {

  @Test
  public void singleNumber() {
    Solution2 instance = new Solution2();
    Assert.assertEquals(1, instance.singleNumber(new int[] {2, 2, 1}));
    Assert.assertEquals(4, instance.singleNumber(new int[] {4, 1, 2, 1, 2}));
    System.out.println(5 ^ 5);
    System.out.println(Integer.toBinaryString(5));
    System.out.println(Integer.toBinaryString(-5));
    int value = (5 ^ -5);
    System.out.println(Integer.toBinaryString(value));
    System.out.println(Integer.toBinaryString(128 ^ 64));
  }
}
