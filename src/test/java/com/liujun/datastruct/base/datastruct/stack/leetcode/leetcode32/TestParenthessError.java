package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode32;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/08
 */
public class TestParenthessError {

  private SolutionError instance = new SolutionError();

  @Test
  public void testSimple2() {

    this.check(")()())", 4);
    this.check("(()", 2);
    this.check("())", 2);
    this.check("()(()", 2);
    this.check("()(())", 6);
  }

  private void check(String input, int checkNum) {
    int outRsp = instance.longestValidParentheses(input);

    Assert.assertEquals(checkNum, outRsp);
  }
}
