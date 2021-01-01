package com.liujun.datastruct.base.search.binarysearch2.leetcode278;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试找出第一个错误的版本
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private VersionControl instance = new Solution();

  @Test
  public void testSolution1() {
      this.invoke(5,3);
      this.invoke(1000,20);
      this.invoke(2123,32);
      this.invoke(1231,67);
  }

  private void invoke(int n, int target) {
    instance.setFirstBedVersion(target);
    int firstVersion = instance.firstBadVersion(n);
    Assert.assertEquals(target, firstVersion);
  }
}
