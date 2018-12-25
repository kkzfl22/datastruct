package com.liujun.datastruct.datastruct.heap.solution.topK;

import org.junit.Test;

import java.util.Arrays;

/**
 * 进行topk的计算
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class TestTopkCount {

  private TopkCount instance = new TopkCount();

  @Test
  public void taskCount() {
    int[] data = new int[] {1,2,3,4,5,6,7,8,9,10};

    int[] out = instance.topk(data, 5);
    System.out.println(Arrays.toString(out));
  }
}
