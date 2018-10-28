package com.liujun.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestMergeSort {

  private MergeSort mergeInstance = new MergeSort();

  @Test
  public void margeSort() {

    int[] data = new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1};

    mergeInstance.mergeSort(data);

    System.out.println(Arrays.toString(data));
  }
}
