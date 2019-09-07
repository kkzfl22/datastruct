package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode227;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试表达式的运算
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/16
 */
public class TestSolutionOther {

  @Test
  public void testcalculate() {

    SolutionOther1 instance = new SolutionOther1();

    Assert.assertEquals(7, instance.calculate("3+2*2"));
    Assert.assertEquals(1, instance.calculate("3/2"));
    Assert.assertEquals(5, instance.calculate("3+5 / 2"));
    Assert.assertEquals(3, instance.calculate("1+1+1"));
    Assert.assertEquals(6, instance.calculate("1+2*5/3+6/4*2"));
    Assert.assertEquals(-24, instance.calculate("1*2-3/4+5*6-7*8+9/10"));
    Assert.assertEquals(6056, instance.calculate("1*2*3*10+10/2/5-2-2-1+10*20*30"));
  }

  @Test
  public void testRuncount() {
    SolutionOther2 instance = new SolutionOther2();

    Assert.assertEquals(7, instance.calculate("3+2*2"));
    Assert.assertEquals(1, instance.calculate("3/2"));
    Assert.assertEquals(5, instance.calculate("3+5 / 2"));
    Assert.assertEquals(3, instance.calculate("1+1+1"));
    Assert.assertEquals(6, instance.calculate("1+2*5/3+6/4*2"));
    Assert.assertEquals(-24, instance.calculate("1*2-3/4+5*6-7*8+9/10"));
    Assert.assertEquals(6056, instance.calculate("1*2*3*10+10/2/5-2-2-1+10*20*30"));
  }
}
