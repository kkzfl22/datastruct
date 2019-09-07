package com.liujun.datastruct.base.datastruct.stack.codeimplement;

import com.liujun.datastruct.base.datastruct.stack.implement.MyArrayStack;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
      Assert.assertEquals(stack.size(), i + 1);
    }

    int maxValue = max - 1;

    while (maxValue > 0) {
      int poValue = stack.pop();
      Assert.assertEquals(maxValue, poValue);
      maxValue--;
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testStackOutofBounds() {

    int max = 100;

    MyArrayStack stack = new MyArrayStack(max);
    for (int i = 0; i < max + 10; i++) {
      stack.push(i);
      Assert.assertEquals(stack.size(), i + 1);
    }
  }

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testStackNegativeArraySize() {

    int max = 100;

    MyArrayStack stack = new MyArrayStack(max);

    int maxValue = max - 1;
    // 数据的放入
    for (int i = 0; i < max; i++) {
      stack.push(i);
      Assert.assertEquals(stack.size(), i + 1);
    }

    // 数据拿取
    while (maxValue > -10) {
      if (maxValue < 0) {
        thrown.expect(NegativeArraySizeException.class);
      }

      int poValue = stack.pop();

      Assert.assertEquals(maxValue, poValue);
      maxValue--;
    }
  }
}
