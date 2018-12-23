package com.liujun.datastruct.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 用于测试计数排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class TestCountSort {

  private CountSort countInstance = new CountSort();

  @Test
  public void countSort() {

    int[] data = new int[] {2, 5, 3, 0, 2, 3, 0, 3};

    int[] arraryData = countInstance.countSort(data);

    System.out.println("结果:"+Arrays.toString(arraryData));
  }
}
