package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code217;

import org.junit.Assert;
import org.junit.Test;

/**
 * 检查是否存在重复元素
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void testExists() {
    Solution instance = new Solution();

    Assert.assertEquals(true, instance.containsDuplicate(new int[] {1, 2, 3, 1}));
    Assert.assertEquals(false, instance.containsDuplicate(new int[] {1, 2, 3, 4}));
    Assert.assertEquals(true, instance.containsDuplicate(new int[] {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}));
  }
}
