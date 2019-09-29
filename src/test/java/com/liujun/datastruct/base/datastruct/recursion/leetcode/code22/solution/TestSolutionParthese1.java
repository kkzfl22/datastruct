package com.liujun.datastruct.base.datastruct.recursion.leetcode.code22.solution;

import org.junit.Test;

import java.util.List;

/**
 * 测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/24
 */
public class TestSolutionParthese1 {

  @Test
  public void testSoltion() {
    int num = 3;

    SolutionParthese1 instance = new SolutionParthese1();
    List<String> dataList = instance.generateParenthesis(num);
    for (String data : dataList) {
      System.out.println(data);
    }
  }

  @Test
  public void testSoltion1() {
    int num = 1;

    SolutionParthese1 instance = new SolutionParthese1();
    List<String> dataList = instance.generateParenthesis(num);
    for (String data : dataList) {
      System.out.println(data);
    }
  }
}
