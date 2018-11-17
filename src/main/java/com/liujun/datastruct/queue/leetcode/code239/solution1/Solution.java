package com.liujun.datastruct.queue.leetcode.code239.solution1;

import java.util.LinkedList;

/**
 * 使用数组来解决，
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class Solution {

  /**
   * 求滑动窗口的最大值
   *
   * 由于需要对每个窗口做求最大数，时间复杂度为O(N*K)
   *
   * @param nums 输入数据集
   * @param k 窗口大小
   * @return 窗口内最大值
   */
  public int[] maxSlidingWindow(int[] nums, int k) {

    if (null == nums) {
      return null;
    }

    if (nums.length == 0) {
      return nums;
    }

    if (k > nums.length) {
      return null;
    }
    int arrayIndex = 0;
    int[] result = new int[nums.length - k + 1];
    // 将初次窗口数据加入到队列中
    LinkedList<Integer> qu = new LinkedList<>();
    for (int i = 0; i < k; i++) {
      qu.addLast(nums[i]);
    }
    // 进行链表排序取最大值
    result[arrayIndex] = max(qu);
    arrayIndex++;

    for (int i = k; i < nums.length; i++) {
      // 当前队列已经满，每次推进，都需要左右移除一位，右边加入一位
      qu.removeFirst();
      qu.addLast(nums[i]);
      result[arrayIndex] = max(qu);
      arrayIndex++;
    }

    return result;
  }

  /**
   * 求最大值
   *
   * @param list 链表
   * @return 集合
   */
  private int max(LinkedList<Integer> list) {
    int max = -1;
    for (Integer i : list) {
      if (i > max) {
        max = i;
      }
    }
    return max;
  }
}
