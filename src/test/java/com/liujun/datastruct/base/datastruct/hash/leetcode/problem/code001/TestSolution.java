package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code001;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 测试两数之和
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void twoSum() {
    Solution instance = new Solution();

    this.assertArray(instance.twoSum(new int[] {2, 7, 11, 15}, 9), new int[] {0, 1});
    this.assertArray(instance.twoSum(new int[] {3, 2, 4}, 6), new int[] {1, 2});
  }

  private void assertArray(int[] src, int[] target) {
    Arrays.sort(src);
    Arrays.sort(target);
    for (int i = 0; i < src.length; i++) {
      Assert.assertEquals(src[i], target[i]);
    }
  }
}
