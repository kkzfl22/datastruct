package com.liujun.datastruct.base.search.binarysearch2;

import org.junit.Assert;
import org.junit.Test;

/**
 * 二分查找测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBinarySearchLast {

  @Test
  public void testSearch() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19, 19};

    BinarySearchLast instance = new BinarySearchLast();
    int searchIndex = instance.search(data, 7);
    Assert.assertEquals(5, searchIndex);
  }

  @Test
  public void testSearchLast() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 13, 15, 19, 19};

    BinarySearchLast instance = new BinarySearchLast();
    int searchIndex = instance.search(data, 19);
    Assert.assertEquals(data.length-1, searchIndex);
  }

  @Test
  public void testSearchNotExists() {
    int[] data = new int[] {1, 3, 5, 7, 7, 7, 10, 13};

    BinarySearchLast instance = new BinarySearchLast();
    int searchIndex = instance.search(data, 8);
    Assert.assertEquals(-1, searchIndex);
  }
}
