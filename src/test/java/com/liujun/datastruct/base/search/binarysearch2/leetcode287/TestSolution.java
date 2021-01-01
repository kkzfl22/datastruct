package com.liujun.datastruct.base.search.binarysearch2.leetcode287;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试查找重复数
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testFindDuplicate() {

    this.findDuplicate(new int[] {1, 3, 4, 2, 2}, 2);
    this.findDuplicate(new int[] {3, 1, 3, 4, 2}, 3);
    this.findDuplicate(new int[] {1, 3, 4, 2, 1}, 1);
  }

  private void findDuplicate(int[] nums, int tarIndex) {
    int index = instance.findDuplicate(nums);
    Assert.assertEquals(index, tarIndex);
  }
}
