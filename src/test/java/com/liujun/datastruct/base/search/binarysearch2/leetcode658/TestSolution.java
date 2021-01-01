package com.liujun.datastruct.base.search.binarysearch2.leetcode658;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
    int[] array = {1, 2, 3, 4, 5};
    this.targetCheck(array, 4, 3, new Integer[] {1, 2, 3, 4});
  }

  @Test
  public void testSolution2() {
    int[] array = {1, 2, 3, 4, 5};
    this.targetCheck(array, 4, -1, new Integer[] {1, 2, 3, 4});
  }

  @Test
  public void testSolution3() {
    int[] array = {1, 1, 1, 10, 10, 10};
    this.targetCheck(array, 1, 9, new Integer[] {10});
  }

  @Test
  public void testSolution4() {
    int[] array = {0,0,0,1,3,5,6,7,8,8};
    this.targetCheck(array, 2, 2, new Integer[] {1,3});
  }

  /**
   * 从数据中找到k个接口x的数
   *
   * @param nums 原始数组
   * @param k 个数
   * @param x 接近值
   * @param value 目标
   */
  private void targetCheck(int[] nums, int k, int x, Integer[] value) {
    List<Integer> resultList = instance.findClosestElements(nums, k, x);
    Assert.assertEquals(resultList.size(), value.length);
    System.out.println(resultList);
    for (Integer item : resultList) {
      Assert.assertThat(item, Matchers.in(value));
    }
  }
}
