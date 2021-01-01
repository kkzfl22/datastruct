package com.liujun.datastruct.base.sort.leetcode.code315.implement;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供非暴力的解法
 *
 * <p>要求解的问题即为求解当前数在排序后的第一个位置，可通过快速排序中的分区函数来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/13
 */
public class SolutionCode315Good {

  public List<Integer> countSmaller(int[] nums) {

    if (nums == null || nums.length == 0) {
      return new ArrayList<>(0);
    }

    List<Integer> result = new ArrayList<>(nums.length);

    int s = 0;
    int t = nums.length;
    int dataIndex = -1;
    for (int i = 0; i < nums.length; i++) {
      if (dataIndex == -1) {
        dataIndex = this.partition(nums, s, t, i);
        result.add(dataIndex);
        continue;
      }

      if (nums[i] > nums[dataIndex]) {
        dataIndex = this.partition(nums, dataIndex, t, i);
      } else {
        dataIndex = this.partition(nums, 0, dataIndex, i);
      }
      result.add(dataIndex);
    }

    return result;
  }

  /**
   * 原地分区函数，使用快速排序中的分区中的分区函数来解决
   *
   * @param data 待分区的数据
   * @return 当前索引位置
   */
  private int partition(int[] data, int s, int t, int point) {
    int left = s;
    for (int right = s; right < t; right++) {
      if (data[right] > data[point]) {
        swap(data, left, right);
        left++;
      }
    }
    // 交换位置
    swap(data, left, point);
    return left;
  }

  private void swap(int[] data, int s, int t) {
    int tmp = data[t];
    data[t] = data[s];
    data[s] = tmp;
  }
}
