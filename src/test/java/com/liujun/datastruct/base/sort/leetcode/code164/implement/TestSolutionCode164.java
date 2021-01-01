package com.liujun.datastruct.base.sort.leetcode.code164.implement;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 测试排序计算间距
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/14
 */
public class TestSolutionCode164 {

  private SolutionCode164 instance = new SolutionCode164();

  @Test
  public void runSortLenght() {
    int[] data = new int[] {3, 6, 9, 1};
    int dataLength = instance.maximumGap(data);

    System.out.println(Arrays.toString(data));

    Assert.assertEquals(3, dataLength);
  }
}
