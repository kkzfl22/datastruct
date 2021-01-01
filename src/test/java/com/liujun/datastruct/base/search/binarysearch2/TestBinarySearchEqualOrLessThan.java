package com.liujun.datastruct.base.search.binarysearch2;

import org.junit.Assert;
import org.junit.Test;

/**
 * 二分查找测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBinarySearchEqualOrLessThan {

  @Test
  public void testSearch() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19};

    BinarySearchEqualOrLessThan instance = new BinarySearchEqualOrLessThan();
    int searchIndex = instance.search(data, 14);
    Assert.assertEquals(6, searchIndex);
  }


  @Test
  public void testSearchMin() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19};

    BinarySearchEqualOrLessThan instance = new BinarySearchEqualOrLessThan();
    int searchIndex = instance.search(data, 6);
    Assert.assertEquals(2, searchIndex);
  }


  @Test
  public void testSearchLess() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19};

    BinarySearchEqualOrLessThan instance = new BinarySearchEqualOrLessThan();
    int searchIndex = instance.search(data, 8);
    Assert.assertEquals(5, searchIndex);
  }

  @Test
  public void testSearchMax() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 10, 13};

    BinarySearchEqualOrLessThan instance = new BinarySearchEqualOrLessThan();
    int searchIndex = instance.search(data, 19);
    Assert.assertEquals(7, searchIndex);
  }

  @Test
  public void testSearchNotExists() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19};

    BinarySearchEqualOrLessThan instance = new BinarySearchEqualOrLessThan();
    int searchIndex = instance.search(data, 0);
    Assert.assertEquals(-1, searchIndex);
  }
}
