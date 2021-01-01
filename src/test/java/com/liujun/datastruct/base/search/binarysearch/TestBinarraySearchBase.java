package com.liujun.datastruct.base.search.binarysearch;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestBinarraySearchBase {

  @Test
  public void testBinarySearch() {

    BinarraySearchBase searchInstance = new BinarraySearchBase();

    int[] data = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 22, 33, 44};

    for (int i = 0; i <= data.length - 1; i++) {
      int index = searchInstance.arraySearch(data, data[i]);
      System.out.println("查找的索引下标为:" + index);
    }
  }
}
