package com.liujun.datastruct.base.datastruct.array.leetcode41;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试查找最小正整数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/27
 */
public class TestSolution {

  @Test
  public void testfirstMissingPositive() {
    int[] array = new int[] {1, 2, 0};

    Solution instance = new Solution();
    int result = instance.firstMissingPositive(array);
    Assert.assertEquals(3, result);
  }

  @Test
  public void testfirstMissingPositive2() {
    int[] array = new int[] {3, 4, -1, 1};

    Solution instance = new Solution();
    int result = instance.firstMissingPositive(array);
    Assert.assertEquals(2, result);
  }

  @Test
  public void testfirstMissingPositive3() {
    int[] array = new int[] {7, 8, 9, 11, 12};

    Solution instance = new Solution();
    int result = instance.firstMissingPositive(array);
    Assert.assertEquals(1, result);
  }

  @Test
  public void testfirstMissingPositiveNull() {
    int[] array = new int[] {};

    Solution instance = new Solution();
    int result = instance.firstMissingPositive(array);
    Assert.assertEquals(1, result);
  }

  @Test
  public void testfirstMissingPositiveBig() {
    int[] array = new int[] {999, 500, 1};

    Solution instance = new Solution();
    int result = instance.firstMissingPositive(array);
    Assert.assertEquals(2, result);
  }

  @Test
  public void testfirstMissingPositiveBigNum() {
    int[] array = new int[] {2147483647};

    Solution instance = new Solution();
    int result = instance.firstMissingPositive(array);
    Assert.assertEquals(1, result);
  }
}
