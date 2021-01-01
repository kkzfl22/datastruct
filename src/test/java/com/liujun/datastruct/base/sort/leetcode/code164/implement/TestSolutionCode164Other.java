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
public class TestSolutionCode164Other {

  private SolutionCode164Other instance = new SolutionCode164Other();

  @Test
  public void runSortLenght() {
    int[] data = new int[] {8, 6, 9,  15};
    int dataLength = instance.maximumGap(data);

    System.out.println(Arrays.toString(data));
    System.out.println(dataLength);

    Assert.assertEquals(6, dataLength);
  }
}
