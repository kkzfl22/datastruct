package com.liujun.datastruct.queue.leetcode.code239.solution1;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试使用链表作为队列秋解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class TestSolution {

  @Test
  public void testwindowMax() {
    int[] array = new int[] {1, 3, -1, -3, 5, 3, 6, 7};
    Solution solution = new Solution();
    int[] rsArrays = solution.maxSlidingWindow(array, 3);
    System.out.println(Arrays.toString(rsArrays));
  }
}
