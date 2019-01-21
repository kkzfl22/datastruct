package com.liujun.datastruct.base.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 进行插入排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestInsertSort {

  private InsertSort instance = new InsertSort();

  @Test
  public void insertSort() {
    int[] data = new int[] {5, 2, 8, 7, 4, 2};

    instance.insertSort(data);

    System.out.println(Arrays.toString(data));
  }

  @Test
  public void insertSortMy() {
    int[] data = new int[] {5, 2, 8, 7, 4, 2};

    instance.insertSortMy(data);

    System.out.println(Arrays.toString(data));
  }
}
