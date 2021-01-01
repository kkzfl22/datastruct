package com.liujun.datastruct.base.search.binarysearch2.leetcode033;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSearch() {
    int[] nums = new int[] {4, 5, 6, 7, 0, 1, 2};
    int index = instance.search(nums, 0);
    Assert.assertEquals(index, 4);
  }

  @Test
  public void testSearch2() {
    int[] nums = new int[] {4, 5, 6, 7, 0, 1, 2};
    int index = instance.search(nums, 3);
    Assert.assertEquals(index, -1);
  }

  @Test
  public void testSearch3() {
    int[] nums = new int[] {1};
    int index = instance.search(nums, 0);
    Assert.assertEquals(index, -1);
  }

  @Test
  public void testSearch4() {
    int[] nums = new int[] {4, 5, 6, 7, 0, 1, 2};
    int index = instance.search(nums, 6);
    Assert.assertEquals(index, 2);
  }

  @Test
  public void testSearch5() {
    int[] nums = new int[] {5, 1, 3};
    int index = instance.search(nums, 1);
    Assert.assertEquals(index, 1);
  }

  @Test
  public void testSearch6() {
    int[] nums = new int[] {5, 1, 3};
    int index = instance.search(nums, 5);
    Assert.assertEquals(index, 0);
  }

  @Test
  public void testSearch7() {
    int[] nums = new int[] {5, 1, 3};
    int index = instance.search(nums, 3);
    Assert.assertEquals(index, 2);
  }

  @Test
  public void testSearch8() {
    int[] nums = new int[] {7, 8, 1, 2, 3, 4, 5, 6};
    int index = instance.search(nums, 2);
    Assert.assertEquals(index, 3);
  }
}
