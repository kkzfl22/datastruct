package com.liujun.search.binarysearch;

import org.junit.Test;

/**
 * 测试查找最后一个值等于给定元素的值
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class TestBinarySearchLast {

  private BinarySearchLast lastInstance = new BinarySearchLast();

  @Test
  public void testSearchLast() {
    int[] data = new int[] {1, 2, 3, 4, 5, 5, 5, 6, 6, 6, 7, 8, 9, 10};

    int index = lastInstance.binarySearch(data, 5);
    System.out.println("查找5的索引下标为:" + index);

    index = lastInstance.binarySearch(data, 6);
    System.out.println("查找最后一个为6的元素下标:" + index);
  }
}
