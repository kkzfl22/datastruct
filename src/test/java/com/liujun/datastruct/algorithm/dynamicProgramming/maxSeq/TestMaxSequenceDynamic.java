package com.liujun.datastruct.algorithm.dynamicProgramming.maxSeq;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/10
 */
public class TestMaxSequenceDynamic {

  @Test
  public void testDynamic() {
    int[] arrays = new int[] {2, 9, 3, 6, 5, 1, 7};
    MaxSequenceDynamic instance = new MaxSequenceDynamic();
    instance.countDynamic(arrays);
  }

  @Test
  public void testDynamic2() {
    int[] arrays = new int[] {6, 8, 2, 3, 5, 11, 7, 9, 13, 19, 20};
    MaxSequenceDynamic instance = new MaxSequenceDynamic();
    instance.countDynamic(arrays);
  }
}
