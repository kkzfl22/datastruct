package com.liujun.datastruct.base.sort;

import org.junit.Test;

/**
 * 测试按年龄进行排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/31
 */
public class TestCountSortAge {

  @Test
  public void testAgeSort() {

    // int[] ages = new int[]{3,9,5,3,7,8,9,4};
    int[] ages = new int[] {9, 10, 5, 6, 7, 5, 6};

    CountSortAge count = new CountSortAge();
    count.sortAge(ages);
  }

  @Test
  public void testOptimAgeSort() {

    // int[] ages = new int[]{3,9,5,3,7,8,9,4};
    int[] ages = new int[] {9, 10, 5, 6, 7, 5, 6};

    CountSortAge count = new CountSortAge();
    count.sortAgeOpTim(ages);
  }
}
