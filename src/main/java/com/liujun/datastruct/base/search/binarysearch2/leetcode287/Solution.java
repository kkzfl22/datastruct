package com.liujun.datastruct.base.search.binarysearch2.leetcode287;

/**
 * 要实现在一个数组中查找重复数，就需要使用一个数组的统计，下标为索引，值为最当前小于出现的个数
 *
 * <p>当小于出现的个数时，说明当前还出现重复
 *
 * <p>但当出现重复时，一定大于当前的索引下标
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int findDuplicate(int[] nums) {

    if (nums == null || nums.length < 2) {
      return -1;
    }

    int low = 0, mid, high = nums.length - 1;

    int last = 0;
    while (low <= high) {
      mid = low + (high - low) / 2;
      int result = 0;
      // 用于计数统计得到当前小于元素的个数
      for (int i = 0; i < nums.length; i++) {
        result += nums[i] <= mid ? 1 : 0;
      }
      // 当前未出现重复元素，继续缶右查找
      if (result <= mid) {
        low = mid + 1;
      } else {
        high = mid - 1;
        // 记得下最后一次出现的元素
        last = mid;
      }
    }

    return last;
  }
}
