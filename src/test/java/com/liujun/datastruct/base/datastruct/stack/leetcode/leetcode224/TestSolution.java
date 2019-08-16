package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode224;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试计算器的功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class TestSolution {

  @Test
  public void testRunCount() {
    //    SolutionCalculator instance = new SolutionCalculator();
    //SolutionWeb instance = new SolutionWeb();
    SolutionCount instance = new SolutionCount();

    Assert.assertEquals(322, instance.calculate("311 + 11"));
    Assert.assertEquals(2, instance.calculate("1 + 1"));

    Assert.assertEquals(3, instance.calculate("2 - 1 + 2"));
    Assert.assertEquals(23, instance.calculate("(1+ ( 4+5+2)- 3)+(6+8)"));
    Assert.assertEquals(9, instance.calculate("(1+(4+5+2)-3)"));
    Assert.assertEquals(9, instance.calculate("(1+4+5+2-3)"));
    Assert.assertEquals(30, instance.calculate("  30"));
    Assert.assertEquals(3, instance.calculate("2-(5-6)"));
  }

  @Test
  public void testGetNums() {
    String value = "10";

    char[] data = value.toCharArray();

    int parseValue = 0;

    int index = 0;

    while (index < data.length && Character.isDigit(data[index])) {
      parseValue = parseValue * 10 + data[index++] - '0';
    }

    System.out.println(parseValue);
    System.out.println(":---");

  }
}
