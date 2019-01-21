package com.liujun.datastruct.base.sort.sample;

import com.liujun.datastruct.base.sort.demo.MergeSortSample;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestMergeSortSample {

  @Test
  public void merge() {
    int[] data = new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1};
    MergeSortSample.mergeSort(data, data.length);
    System.out.println(Arrays.toString(data));
  }
}
