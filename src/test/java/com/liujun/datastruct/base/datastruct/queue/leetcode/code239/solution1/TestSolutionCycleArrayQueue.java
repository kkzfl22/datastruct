package com.liujun.datastruct.base.datastruct.queue.leetcode.code239.solution1;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.hamcrest.CoreMatchers;

import java.util.Arrays;

/**
 * 测试使用链表作为队列秋解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class TestSolutionCycleArrayQueue {

  @Test
  public void testwindowMax() {
    int[] array = new int[] {1, 3, -1, -3, 5, 3, 6, 7};
    SolutionCycleArrayQueue solution = new SolutionCycleArrayQueue();
    int[] rsArrays = solution.maxSlidingWindow(array, 3);
    System.out.println(Arrays.toString(rsArrays));
    Assert.assertArrayEquals(new int[] {3, 3, 5, 5, 6, 7}, rsArrays);

  }
}
