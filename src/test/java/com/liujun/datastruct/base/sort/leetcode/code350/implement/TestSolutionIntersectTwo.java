package com.liujun.datastruct.base.sort.leetcode.code350.implement;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试交集
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/26
 */
public class TestSolutionIntersectTwo {

  private SolutionIntersectTwo instance = new SolutionIntersectTwo();

  @Test
  public void outCount() {
    int[] nums1 = new int[] {1, 2, 2, 1};
    int[] nums2 = new int[] {2, 2};
    int[] outdata = instance.intersect(nums1, nums2);
    System.out.println(Arrays.toString(outdata));
  }

  @Test
  public void outCount2() {
    int[] nums1 = new int[] {4, 9, 5};
    int[] nums2 = new int[] {9, 4, 9, 8, 4};
    int[] outdata = instance.intersect(nums1, nums2);
    System.out.println(Arrays.toString(outdata));
  }

  @Test
  public void outCount3() {
    int[] nums1 = new int[] {2, 1};
    int[] nums2 = new int[] {1, 1};
    int[] outdata = instance.intersect(nums1, nums2);
    System.out.println(Arrays.toString(outdata));
  }

  @Test
  public void outCount4() {
    int[] nums1 = new int[] {1};
    int[] nums2 = new int[] {1};
    int[] outdata = instance.intersect(nums1, nums2);
    System.out.println(Arrays.toString(outdata));
  }
}
