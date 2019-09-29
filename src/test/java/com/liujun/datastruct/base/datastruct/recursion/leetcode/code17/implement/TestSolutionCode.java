package com.liujun.datastruct.base.datastruct.recursion.leetcode.code17.implement;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 测试案例
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/20
 */
public class TestSolutionCode {

  @Test
  public void testSolutionCode() {
    SolutionCode instance = new SolutionCode();

    List<String> expResult1 =
        Arrays.asList(new String[] {"ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"});

    System.out.println(instance.better("23"));
  }
}
