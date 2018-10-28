package com.liujun.sort.sample;

import com.liujun.sort.demo.QuickSortSemple;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestQuickSortSemple {

  @Test
  public void quickSort() {
    int[] data = new int[] {6, 11, 3, 9, 8};
    QuickSortSemple.quickSort(data, data.length);

    System.out.println(Arrays.toString(data));
  }
}
