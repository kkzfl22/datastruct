package com.liujun.datastruct.base.datastruct.stack.codeimplement;

import com.liujun.datastruct.base.datastruct.stack.implement.MyLinkedStack;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
      Assert.assertEquals(maxValue, poValue);
      maxValue--;
    }
  }

  /** 进行添加超过大小的测试 */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testMoreAdd() {

    int max = 100;

    MyLinkedStack stack = new MyLinkedStack(max);
    for (int i = 0; i < max + 10; i++) {
      stack.push(i);
      Assert.assertEquals(stack.size(), i + 1);
    }
  }

  @Rule public ExpectedException thrown = ExpectedException.none();

  /** 进行添加超过大小的测试 */
  @Test
  public void testMoreUse() {

    int max = 100;

    MyLinkedStack stack = new MyLinkedStack(max);
    for (int i = 0; i < max; i++) {
      stack.push(i);
      Assert.assertEquals(stack.size(), i + 1);
    }

    int maxValue = max - 1;

    while (maxValue > -10) {

      if (maxValue < 0) {
        thrown.expect(NullPointerException.class);
      }

      int poValue = stack.pop();
      // System.out.println("获取出来的值:" + poValue);

      Assert.assertEquals(maxValue, poValue);
      maxValue--;
    }
  }
}
