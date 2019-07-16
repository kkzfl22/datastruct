package com.liujun.datastruct.base.datastruct.array.leetcode15;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试三数之和的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/15
 */
public class TestCountSumThirdNumArraysThird {

  @Test
  public void test() {
    int[] array = new int[] {-1, 0, 1, 2, -1, -4, 4};

    List<List<Integer>> arrays = CountSumThirdNumArraysThird.INSTANCE.threeSum(array);

    System.out.println(arrays);

    Assert.assertEquals(3, arrays.size());
  }

  @Test
  public void test2() {
    int[] array = new int[] {-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0};

    List<List<Integer>> arrays = CountSumThirdNumArraysThird.INSTANCE.threeSum(array);

    System.out.println(arrays);

    Assert.assertEquals(6, arrays.size());
  }

  @Test
  public void test3() {
    int[] array = new int[] {0, 0, 0};

    List<List<Integer>> arrays = CountSumThirdNumArraysThird.INSTANCE.threeSum(array);

    System.out.println(arrays);

    Assert.assertEquals(1, arrays.size());
  }
}
