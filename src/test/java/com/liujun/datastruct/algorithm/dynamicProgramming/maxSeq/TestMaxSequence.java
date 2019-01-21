package com.liujun.datastruct.algorithm.dynamicProgramming.maxSeq;

import org.junit.Test;

import java.util.Arrays;

/**
 * 最大递增子序列的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/08
 */
public class TestMaxSequence {

  @Test
  public void testmaxSeq() {
    int[] arrays = new int[] {2, 9, 3, 6, 5, 1, 7};
    MaxSequence instance = new MaxSequence();
    instance.recursionCount2(arrays, 1);
  }

  @Test
  public void testmaxSeqDynamic() {
    int[] arrays = new int[] {2, 9, 3, 6, 5, 1, 7};
    MaxSequence instance = new MaxSequence();
    instance.longestIncreaseSubArrayDP(arrays);
  }

  @Test
  public void testrecursion4() {
    int[] arrays = new int[] {2, 9, 3, 6, 5, 1, 7};
    MaxSequence instance = new MaxSequence();
    int maxNum = instance.recursionCount4(arrays, arrays.length - 1);
    System.out.println("maxnum :" + maxNum);
  }

  @Test
  public void testmaxSeq3() {
    int[] arrays = new int[] {6, 8, 2, 3, 5, 11, 7, 9, 13, 19, 20};
    MaxSequence instance = new MaxSequence();

    instance.recursionCount3(arrays, 1);

    System.out.println(Arrays.toString(instance.status));
  }

  @Test
  public void testmaxSeqdemo() {
    int[] arrays = new int[] {6, 8, 2, 3, 5, 11, 7, 9, 13, 19, 20};
    MaxSequence instance = new MaxSequence();
    int max = instance.longestIncreaseSubArrayDP(arrays);
    System.out.println("currmax :" + max);
  }

  @Test
  public void testrecursion44() {
    int[] arrays = new int[] {6, 8, 2, 3, 5, 11, 7, 9, 13, 19, 20};
    MaxSequence instance = new MaxSequence();
    int maxNum = instance.recursionCount4(arrays, arrays.length - 1);
    System.out.println("maxnum :" + maxNum);
  }


}
