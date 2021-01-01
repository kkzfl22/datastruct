package com.liujun.datastruct.base.sort.leetcode.code75.implement;

import org.junit.Test;

/**
 * 测试颜色排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/10
 */
public class TestSolutionCode75 {

  @Test
  public void colorSort() {
    SolutionCode75 instance = new SolutionCode75();

    int[] data = new int[] {2, 0, 2, 1, 1, 0,1};
    instance.sortColors(data);
    for (int valueItem : data) {
      System.out.println(valueItem);
    }
  }
}
