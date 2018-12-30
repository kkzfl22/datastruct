package com.liujun.datastruct.datastruct.queue.leetcode.code239.solution1;

import org.junit.Test;

import java.util.Arrays;

/**
 * 使用大顶堆来解决滑动窗口的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestSolutionPriorityQueue {


    @Test
    public void testPriorityQueue()
    {
        int[] array = new int[] {1, 3, -1, -3, 5, 3, 6, 7};
        SolutionPriorityQueue instance = new SolutionPriorityQueue();
        int[] result = instance.maxSlidingWindow(array,3);
        System.out.println(Arrays.toString(result));
    }

}
