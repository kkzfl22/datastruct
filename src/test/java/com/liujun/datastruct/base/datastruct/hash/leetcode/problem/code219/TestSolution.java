package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code219;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试当前的问题的解
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  /** 测试问题的解 */
  @Test
  public void countIndex() {
    this.assertCheck(new int[] {1, 2, 3, 1}, 3, true);
    this.assertCheck(new int[] {1, 0, 1, 1}, 1, true);
    this.assertCheck(new int[] {1, 2, 3, 1, 2, 3}, 2, false);
  }

  private void assertCheck(int[] data, int k, boolean outCheck) {
    boolean rsp = instance.containsNearbyDuplicate(data, k);
    Assert.assertEquals(rsp, outCheck);
  }
}
