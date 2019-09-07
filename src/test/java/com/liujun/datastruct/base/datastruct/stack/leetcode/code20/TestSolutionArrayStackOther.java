package com.liujun.datastruct.base.datastruct.stack.leetcode.code20;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试括号是否匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class TestSolutionArrayStackOther {

  private SolutionArrayStackOther solu = new SolutionArrayStackOther();

  @Test
  public void testIsValid() {

    check("([{() }])", true);
    check("([{ }])", true);
    check("]", false);
    check("([{ }])", true);
    check("()", true);
    check("()[]{}", true);
    check("(]", false);
    check("([)]", false);
    check("{[]}", true);
  }

  private void check(String value, boolean rsp) {
    boolean isValue = solu.isValid(value);

    Assert.assertEquals(rsp, isValue);
  }
}
