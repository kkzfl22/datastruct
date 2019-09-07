package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode224;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * 进行多项式的运算操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/16
 */
public class TestSolutionStack {

  @Test
  public void testExpress() {
    SolutionStack instance = new SolutionStack();

    Stack<Integer> stackInt = new Stack<>();
    stackInt.push(9);
    stackInt.push(3);
    stackInt.push(4);
    stackInt.push(5);
    stackInt.push(2);
    stackInt.push(-1);

    Stack<Character> stackOper = new Stack<>();
    stackOper.push('/');
    stackOper.push('*');
    stackOper.push('-');
    stackOper.push('*');
    stackOper.push('-');

    int countOut = instance.countExpress(stackInt, stackOper);
    Assert.assertEquals(3, countOut);
  }

  @Test
  public void testExpRun() {
    SolutionStack instance = new SolutionStack();
    int rsp = instance.calculate("9/3*4-5*2--1");
    Assert.assertEquals(3, rsp);
    Assert.assertEquals(322, instance.calculate("311 + 11"));
    Assert.assertEquals(11, instance.calculate("10--1--1-1"));
    Assert.assertEquals(8, instance.calculate("10--1-(-1-1-1)"));
    Assert.assertEquals(2, instance.calculate("1 + 1"));

    Assert.assertEquals(3, instance.calculate("2 - 1 + 2"));
    Assert.assertEquals(23, instance.calculate("(1+ (4+5+2)- 3)+(6+8)"));
    Assert.assertEquals(56, instance.calculate("(1+ (4+5+2)*8/2- 3)+(6+8)"));
    Assert.assertEquals(146, instance.calculate("(15*3/5*7-8 + (4+(10/5*3+8)+2)*8/2- 3)+(6+8)"));
    Assert.assertEquals(9, instance.calculate("(1+(4+5+2)-3)"));
    Assert.assertEquals(9, instance.calculate("(1+4+5+2-3)"));
    Assert.assertEquals(30, instance.calculate("  30"));
    Assert.assertEquals(3, instance.calculate("2-(5-6)"));
  }
}
