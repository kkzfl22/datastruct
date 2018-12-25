package com.liujun.datastruct.algorithm.divideAndConquerAlgorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/20
 */
public class TestCountSortWord {

  @Test
  public void testCode() {
    CountSortWord sortWord = new CountSortWord();

    int[] arrays = new int[] {1, 6, 5, 2, 3, 4};

    sortWord.countSort(arrays, 0, arrays.length - 1);

    System.out.println(Arrays.toString(arrays));
    System.out.println("结果:" + sortWord.num);
  }
}
