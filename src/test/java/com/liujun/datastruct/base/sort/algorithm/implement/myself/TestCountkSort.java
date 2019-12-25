package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import org.junit.Test;

/**
 * 求得统计数据
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/21
 */
public class TestCountkSort {

  @Test
  public void sortData() {
    int[] srcdata = new int[] {5, 6, 8, 1, 9, 2, 2, 3, 5, 9};
    CountkSort instance = new CountkSort();
    instance.sort(srcdata);
  }
}
