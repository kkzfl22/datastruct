package com.liujun.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试选择排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class TestSelectSort {

  private SelectSort sortInst = new SelectSort();

  @Test
  public void selectSort() {
    int[] data = new int[] {10, 15, 17, 11, 12, 13};

    sortInst.selectSort(data);

    System.out.println(Arrays.toString(data));
  }
}
