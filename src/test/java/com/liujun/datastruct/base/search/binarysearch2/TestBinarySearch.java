package com.liujun.datastruct.base.search.binarysearch2;

import org.junit.Assert;
import org.junit.Test;

/**
 * 二分查找测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBinarySearch {

  @Test
  public void testSearch() {
    int[] data = new int[] {1, 3, 5, 7, 8, 10, 13};

    BinarySearch instance = new BinarySearch();
    int searchIndex = instance.search(data, 5);
    Assert.assertEquals(2, searchIndex);
  }

  @Test
  public void testSearchNotExists() {
    int[] data = new int[] {1, 3, 5, 7, 8, 10, 13};

    BinarySearch instance = new BinarySearch();
    int searchIndex = instance.search(data, 6);
    Assert.assertEquals(-1, searchIndex);
  }
}
