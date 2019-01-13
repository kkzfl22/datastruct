package com.liujun.datastruct.base.datastruct.queue.leetcode.code703;

import java.util.PriorityQueue;

/**
 * 在一个数据流中找出第K大的元素
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class KthLargest {

  /** 优先队列 */
  private PriorityQueue<Integer> queue;

  /** 堆的大小 */
  private int k;

  public KthLargest(int k, int[] nums) {
    // 维护一个最小堆
    queue = new PriorityQueue<>(k);

    queue = new PriorityQueue<>(10);

    this.k = k;

    for (int s : nums) {
      add(s);
    }
  }

  public int add(int value) {
    // 当数据的规模小于K时直接加入
    if (queue.size() < k) {
      queue.add(value);
    }
    // 是否比当前值小，则移除头，加入队列
    else if (queue.peek() < value) {
      // 移除队列头
      queue.poll();
      // 加入队列中
      queue.offer(value);
    }

    return queue.peek();
  }
}
