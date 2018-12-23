package com.liujun.datastruct.search.binarysearch;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/04
 */
public class TestBinarySearchLess {

  private BinarySearchLess lessInstance = new BinarySearchLess();

  @Test
  public void binarySearch() {
    int[] data = new int[] {1, 2, 3, 6, 8, 10, 24, 29, 33};
    int index = lessInstance.binarySearch(data, 25);
    System.out.println("查找最后一个小于等于给值值的索引 :" + index);
  }
}
