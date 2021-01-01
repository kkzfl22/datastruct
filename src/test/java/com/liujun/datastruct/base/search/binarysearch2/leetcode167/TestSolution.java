package com.liujun.datastruct.base.search.binarysearch2.leetcode167;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试二分的查找目标数
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
    this.assertCheck(new int[] {2, 7, 11, 15}, 9, new int[] {1, 2});
    this.assertCheck(new int[] {2, 7, 11, 15}, 18, new int[] {2, 3});
    this.assertCheck(new int[] {2, 7, 11, 15}, 26, new int[] {3, 4});
    this.assertCheck(new int[] {2, 3, 4}, 6, new int[] {1, 3});
    this.assertCheck(new int[] {3, 24, 50, 79, 88, 150, 345}, 200, new int[] {3, 6});
    this.assertCheck(new int[] {1, 2, 3, 4, 4, 9, 56, 90}, 8, new int[] {4, 5});
  }

  private void assertCheck(int[] src, int target, int[] result) {
    int[] targetValue = instance.twoSum(src, target);
    Assert.assertEquals(targetValue.length, result.length);
    for (int i = 0; i < result.length; i++) {
      Assert.assertEquals(targetValue[i], result[i]);
    }
  }
}
