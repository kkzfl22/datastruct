package com.liujun.datastruct.datastruct.queue.leetcode.code239.solution1;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 使用双端队列来解决此滑动窗口的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class SolutionDequeue {

  /**
   * 求滑动窗口的最大值
   *
   * <p>由于需要对每个窗口做求最大数，时间复杂度为O(N*K)
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

    int[] result = new int[nums.length - k + 1];

    int readIndex = 0;

    // 读取首次记录
    LinkedList<Integer> dequeue = new LinkedList<>();

    for (int i = 0; i < nums.length; i++) {
      // 移除比当前元素小的对象
      while (!dequeue.isEmpty() && nums[dequeue.peekLast()] <= nums[i]) {
        dequeue.pollLast();
      }
      dequeue.add(i);

      // 进行窗口滑动的调整
      while (dequeue.peekFirst() < i - k + 1) {
        dequeue.pollFirst();
      }

      if (i + 1 >= k) {
        result[readIndex++] = nums[dequeue.peekFirst()];
      }
    }

    return result;
  }
}
