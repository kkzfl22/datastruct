package com.liujun.datastruct.algorithm.divideAndConquerAlgorithm.sample;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/20
 */
public class TestDemo {

  @Test
  public void testDemo() {
    int[] arrays = new int[] {1, 5, 6, 2, 3, 4};
    Demo instance = new Demo();
    int countNum = instance.count(arrays, arrays.length);

    System.out.println(Arrays.toString(arrays));
    System.out.println("统计结果:" + countNum);
  }
}
