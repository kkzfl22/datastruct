package com.liujun.datastruct.base.sort.leetcode.code349.implement;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试数组中并集的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/26
 */
public class TestSolutionIntersection {

  private SolutionIntersection instance = new SolutionIntersection();

  @Test
  public void intersection() {
    int[] nums1 = new int[] {1, 2, 2, 1};
    int[] nums2 = new int[] {2, 2};
    int[] outdata = instance.intersection(nums1, nums2);
    System.out.println(Arrays.toString(outdata));
  }


    @Test
    public void intersection2() {
        int[] nums1 = new int[] {4,9,5};
        int[] nums2 = new int[] {9,4,9,8,4};
        int[] outdata = instance.intersection(nums1, nums2);
        System.out.println(Arrays.toString(outdata));
    }
}
