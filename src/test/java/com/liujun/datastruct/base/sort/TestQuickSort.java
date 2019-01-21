package com.liujun.datastruct.base.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestQuickSort {

  private QuickSort quick = new QuickSort();

  @Test
  public void quickSortPattern() {
    int[] data = new int[] {6, 11, 3, 9, 8, 7};

    int index = quick.pattern(data, 0, data.length - 1);

    System.out.println(index + ":" + Arrays.toString(data));
  }

  @Test
  public void quickSort() {
    int[] data = new int[] {6, 11, 3, 9, 8, 7};
    quick.quickSort(data);
    System.out.println(Arrays.toString(data));
  }
}
