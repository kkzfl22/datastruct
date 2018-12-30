package com.liujun.datastruct.datastruct.queue.leetcode.code239.solution1;

import org.junit.Test;

import java.util.Arrays;

/**
 * 进行测试滑动窗口
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestSolutionDequeue {

  @Test
  public void testSolution() {
    int[] array = new int[] {1, 3, -1, -3, 5, 3, 6, 7};
    SolutionDequeue instance = new SolutionDequeue();
    int[] result = instance.maxSlidingWindow(array, 3);
    System.out.println(Arrays.toString(result));
  }
}
