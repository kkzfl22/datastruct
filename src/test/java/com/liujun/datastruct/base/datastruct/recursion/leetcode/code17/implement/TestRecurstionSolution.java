package com.liujun.datastruct.base.datastruct.recursion.leetcode.code17.implement;

import org.junit.Test;

import java.util.List;

/**
 * 进行递归式的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/19
 */
public class TestRecurstionSolution {

  @Test
  public void testCount() {
    String input = "23";
    RecurstionSolution instance = new RecurstionSolution();
    List<String> data = instance.letterCombinations(input);
    System.out.println(data);
  }
}
