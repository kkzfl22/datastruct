package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code136;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试查找只出现一次的数字
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void singleNumber() {
    Solution instance = new Solution();
    Assert.assertEquals(1, instance.singleNumber(new int[] {2, 2, 1}));
    Assert.assertEquals(4, instance.singleNumber(new int[] {4, 1, 2, 1, 2}));
  }
}
