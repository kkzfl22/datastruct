package com.liujun.datastruct.base.search.binarysearch;

import org.junit.Test;

/**
 * 二分查找的代码测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class TestBinarySearch {

  private BinarySearch searchInstance = new BinarySearch();

  @Test
  public void testBinarySearch() {
    int[] data = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 22, 33, 44};

    for (int i = 0; i <= data.length - 1; i++) {
      int index = searchInstance.search(data, data[i]);
      System.out.println("查找的索引下标为:" + index);
    }
  }

  @Test
  public void testBinarySearchRescursion() {
    int[] data = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    for (int i = 0; i <= data.length - 1; i++) {
      int index = searchInstance.binarySearchRecursion(data, data[i]);
      System.out.println("查找的索引下标为:" + index);
    }
  }
}
