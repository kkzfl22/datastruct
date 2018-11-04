package com.liujun.search.binarysearch;

import org.junit.Test;

/**
 * 测试查找第一个值大于给定值
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/02
 */
public class TestBinarySearchGreater {

  private BinarySearchGreater binaryGreater = new BinarySearchGreater();

  @Test
  public void binarySearch() {
    int[] data = new int[] {1, 2, 3, 4, 4, 4, 5, 9, 15};

    int index = binaryGreater.binarySearch(data, 7);

    System.out.println("查找的第一个大于给定值的下标为:" + index);
  }
}
