package com.liujun.datastruct.base.datastruct.queue.leetcode.code239.solution1;

/**
 * 使用双端循环队列来解决求窗口最大值
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/27
 */
public class SolutionCycleArrayQueue {

  /**
   * 求滑动窗口的最大值
   *
   * @param nums 输入数据集
   * @param k 窗口大小
   * @return 窗口内最大值
   */
  public int[] maxSlidingWindow(int[] nums, int k) {
    if (null == nums || nums.length == 0 || nums.length <= k) {
      return nums;
    }
    int[] reslut = new int[nums.length - k + 1];

    //使用一个循环队列
    int[] queue = new int[k];

    // 加载值
    for (int i = 0; i < k; i++) {
      queue[i] = nums[i];
    }

    // 求最大值
    int readIndex = 0;
    reslut[readIndex] = maxValue(queue);
    readIndex++;

    int addnextPosition = 0;

    for (int i = k; i < nums.length; i++) {
      queue[addnextPosition] = Integer.MIN_VALUE;
      queue[addnextPosition] = nums[i];
      reslut[readIndex++] = maxValue(queue);
      addnextPosition = (addnextPosition + 1) % k;
    }

    return reslut;
  }

  private int maxValue(int[] value) {
    int maxValue = value[0];
    for (int i = 1; i < value.length; i++) {
      if (maxValue < value[i]) {
        maxValue = value[i];
      }
    }
    return maxValue;
  }
}
