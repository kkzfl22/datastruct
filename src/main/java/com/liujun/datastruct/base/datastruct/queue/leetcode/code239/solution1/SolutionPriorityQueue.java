package com.liujun.datastruct.base.datastruct.queue.leetcode.code239.solution1;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 使用大顶堆来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class SolutionPriorityQueue {

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
    int arrayIndex = 0;
    int[] result = new int[nums.length - k + 1];

    // 将初次窗口数据加入到队列中
    PriorityQueue<Integer> qu =
        new PriorityQueue<>(
            (o1, o2) -> {
              if (o1 < o2) {
                return 1;
              } else if (o1 > o2) {
                return -1;
              }
              return 0;
            });

    int readIndex = 0;

    for (int i = 0; i < k; i++) {
      qu.offer(nums[readIndex]);
      readIndex++;
    }
    result[arrayIndex] = qu.peek();
    arrayIndex++;

    int delIndex = 0;

    do {
      // 移除元素
      int delValue = nums[delIndex];
      qu.remove(delValue);
      delIndex++;
      // 添加元素
      qu.offer(nums[readIndex]);
      readIndex++;
      // 获取最大值
      result[arrayIndex] = qu.peek();
      arrayIndex++;
    } while (readIndex < nums.length);

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
