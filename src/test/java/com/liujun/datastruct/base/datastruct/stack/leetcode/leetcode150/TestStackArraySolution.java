package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode150;

import org.junit.Assert;
import org.junit.Test;

/**
 * 逆波兰式的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class TestStackArraySolution {

  @Test
  public void runcount() {
    EvaluateSolution instance = new EvaluateSolution();

    String[] runcount = new String[] {"2", "1", "+", "3", "*"};

    int value = instance.evalRPN(runcount);

    Assert.assertEquals(9, value);

    String[] runcount2 =
        new String[] {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};

    int value2 = instance.evalRPN(runcount2);

    Assert.assertEquals(22, value2);

  }
}
