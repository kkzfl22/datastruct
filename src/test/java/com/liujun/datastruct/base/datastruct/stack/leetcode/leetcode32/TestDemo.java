package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode32;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * 测试网上的demo
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/10
 */
public class TestDemo {

  @Test
  public void testComplex() {

    this.check(")()())()()((()))", 10);
    this.check(")(((()))()", 8);
    this.check("(()", 2);
    this.check("())", 2);
    this.check("()(()", 2);
    this.check("()(())", 6);
    this.check("(())", 4);
    this.check("()((((()))", 6);
    this.check(")(((()))()", 8);
    this.check("(()))())(", 4);
  }

  private void check(String input, int checkNum) {
    int outRsp = this.longestValidParentheses(input);

    Assert.assertEquals(checkNum, outRsp);
  }

  public int longestValidParentheses(String s) {
    if (s == null || s.length() < 1) return 0;
    Stack<Integer> stack = new Stack<Integer>();
    int count = 0, left = -1;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') stack.push(i);
      else {
        if (!stack.isEmpty()) {
          stack.pop();
          if (!stack.isEmpty()) count = Math.max(count, i - stack.peek());
          else count = Math.max(count, i - left);
        } else left = i;
      }
    }
    return count;
  }
}
