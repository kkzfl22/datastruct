package com.liujun.datastruct.base.search.binarysearch2.leetcode704;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution1() {
    int[] src = {-1, 0, 3, 5, 9, 12};
    this.searchCheck(src, 9, 4);
  }

  @Test
  public void testSolution2() {
    int[] src = {-1, 0, 3, 5, 9, 12};
    this.searchCheck(src, 2, -1);
  }

  private void searchCheck(int[] src, int target, int assertTarget) {
    int searchIndex = instance.search(src, target);
    Assert.assertEquals(searchIndex, assertTarget);
  }
}
