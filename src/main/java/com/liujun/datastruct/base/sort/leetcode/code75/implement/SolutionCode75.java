package com.liujun.datastruct.base.sort.leetcode.code75.implement;

/**
 * sort colors
 *
 * <p>使用计数排序算法来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/06
 */
public class SolutionCode75 implements SolutionCode75Inf {

  public void sortColors(int[] nums) {
    if (null == nums || nums.length <= 1) {
      return;
    }

    // 1,由于数据的范围固定，声明一个3个数组大小的统计数组
    int[] data = new int[3];
    // 2，每一次遍历进行统计
    for (int i = 0; i < nums.length; i++) {
      data[nums[i]]++;
    }

    // 将统计数据写回原数组中
    int index = 0;
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i]; j++) {
        nums[index] = i;
        index++;
      }
    }
  }
}
