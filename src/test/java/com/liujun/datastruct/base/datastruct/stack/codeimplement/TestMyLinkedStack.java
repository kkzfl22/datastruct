package com.liujun.datastruct.base.datastruct.stack.codeimplement;

import com.liujun.datastruct.base.datastruct.stack.implement.MyLinkedStack;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试链式栈
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/14
 */
public class TestMyLinkedStack {

  @Test
  public void testLinkStack() {
    int max = 100;

    MyLinkedStack stack = new MyLinkedStack(max);
    for (int i = 0; i < max; i++) {
      stack.push(i);
      Assert.assertEquals(stack.size(), i + 1);
    }

    int maxValue = max - 1;

    while (maxValue > 0) {
      int poValue = stack.pop();
      System.out.println("当前获取出来的值:" + poValue);
      Assert.assertEquals(maxValue, poValue);
      maxValue--;
    }
  }
}
