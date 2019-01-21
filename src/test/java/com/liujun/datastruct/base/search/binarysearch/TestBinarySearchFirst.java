package com.liujun.datastruct.base.search.binarysearch;

import org.junit.Test;

/**
 * 进行二分查找第一个给定值
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class TestBinarySearchFirst {

  private BinarySearchFirst binaryFirst = new BinarySearchFirst();

  @Test
  public void binarySearch() {
    int[] data = new int[] {1, 2, 3, 4, 4, 4, 5, 6, 7, 8, 9, 10};

    int index = binaryFirst.binarySearch(data, 4);

    System.out.println("查找的索引下标为:" + index);
  }
}
