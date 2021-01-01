package com.liujun.datastruct.base.sort.leetcode.code315.implement;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 测试比较数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/15
 */
public class TestSolutionCode315 {

  private SolutionCode315Good instance = new SolutionCode315Good();
//  private SolutionCode315 instance = new SolutionCode315();

  @Test
  public void testCoun() {
    int[] data = new int[] {5, 2, 6, 1};
    List<Integer> listData = instance.countSmaller(data);
    System.out.println(listData);
  }
}
