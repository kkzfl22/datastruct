package com.liujun.sort;

import org.junit.Test;

/**
 * 测试冒泡排序算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestBubbleSort {

  private BubbleSort sort = new BubbleSort();

  @Test
  public void bubbleSort() {

    int[] data = new int[] {5, 2, 18, 1, 20, 3, 5, 7};

    sort.bobbleSort(data);

    System.out.println();
  }

  @Test
  public void bubbleSortOptimizing() {
    int[] data = new int[] {5, 2, 18, 1, 20, 3, 5, 7};

    sort.bobbleSortOptimizing(data);

      System.out.println();
  }
}
