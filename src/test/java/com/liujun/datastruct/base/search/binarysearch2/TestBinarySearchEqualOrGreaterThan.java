package com.liujun.datastruct.base.search.binarysearch2;

import org.junit.Assert;
import org.junit.Test;

/**
 * 二分查找测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBinarySearchEqualOrGreaterThan {

  @Test
  public void testSearch() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19};

    BinarySearchEqualOrGreaterThan instance = new BinarySearchEqualOrGreaterThan();
    int searchIndex = instance.search(data, 6);
    Assert.assertEquals(3, searchIndex);
  }

  @Test
  public void testSearchNotExists() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 10, 13};

    BinarySearchEqualOrGreaterThan instance = new BinarySearchEqualOrGreaterThan();
    int searchIndex = instance.search(data, 19);
    Assert.assertEquals(-1, searchIndex);
  }

  @Test
  public void testSearchMin() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19};

    BinarySearchEqualOrGreaterThan instance = new BinarySearchEqualOrGreaterThan();
    int searchIndex = instance.search(data, 0);
    Assert.assertEquals(0, searchIndex);
  }
}
