package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode32;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/08
 */
public class TestParenthessMatch {

  private SolutionMatch instance = new SolutionMatch();

  @Test
  public void testComplex() {

    this.check(")()())()()((()))", 10);
    this.check(")(((()))()", 6);
    this.check("(()", 2);
    this.check("())", 2);
    this.check("()(()", 2);
    this.check("()(())", 6);
    this.check("()((((()))", 6);
  }

  private void check(String input, int checkNum) {
    int outRsp = instance.longestValidParentheses(input);

    Assert.assertEquals(checkNum, outRsp);
  }

  @Test
  public void testSimple() {

    this.checkSimple("()", 2);
    this.checkSimple("((())())", 8);
    this.checkSimple("()(((((", 2);
    this.checkSimple("()((((()))", 6);
  }

  private void checkSimple(String input, int checkNum) {
    int outRsp = instance.parenthess(input);

    Assert.assertEquals(checkNum, outRsp);
  }

  @Test
  public void testSimpleBedSegment() {

    this.checkSimpleSegment("())(((()))", 6);
    this.checkSimpleSegment("()((((()))", 6);
  }

  private void checkSimpleSegment(String input, int checkNum) {
    int outRsp = instance.parenthessSegment(input);

    Assert.assertEquals(checkNum, outRsp);
  }

  @Test
  public void testParenthessLast() {

    this.checkParenthessLast("())(((()))", 6);
    this.checkParenthessLast("()((((()))", 6);
    this.checkParenthessLast(")()())()()((()))", 10);
    this.checkParenthessLast(")(((()))()", 8);
    this.checkParenthessLast("(()", 2);
    this.checkParenthessLast("())", 2);
    this.checkParenthessLast("()(()", 2);
    this.checkParenthessLast("()(())", 6);
    this.checkParenthessLast("()((((()))", 6);
    this.checkParenthessLast("(()))())(", 4);
  }

  private void checkParenthessLast(String input, int checkNum) {
    int outRsp = instance.parenthessLast(input);

    Assert.assertEquals(checkNum, outRsp);
  }
}
