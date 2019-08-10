package com.liujun.datastruct.base.datastruct.stack.codeimplement;

import com.liujun.datastruct.base.datastruct.stack.implement.MyArrayStack;
import org.junit.Assert;
import org.junit.Test;

/**
 * 进行栈的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/07
 */
public class TestMyArrayStack {

  @Test
  public void testStack() {

    int max = 100;

    MyArrayStack stack = new MyArrayStack(max);
    for (int i = 0; i < max; i++) {
      stack.push(i);
      Assert.assertEquals(stack.sisze(), i + 1);
    }

    int maxValue = max - 1;

    while (maxValue > 0) {
      int poValue = stack.pop();
      Assert.assertEquals(maxValue, poValue);
      maxValue--;
    }
  }
}
