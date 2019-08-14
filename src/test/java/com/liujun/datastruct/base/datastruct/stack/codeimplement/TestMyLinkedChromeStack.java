package com.liujun.datastruct.base.datastruct.stack.codeimplement;

import com.liujun.datastruct.base.datastruct.stack.implement.MyLinkedChromeStack;
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
public class TestMyLinkedChromeStack {

  @Test
  public void testLinkStack() {
    int max = 100;

    MyLinkedChromeStack stack = new MyLinkedChromeStack(max);
    // 进行数据放入
    for (int i = 0; i < max; i++) {
      stack.push(String.valueOf(i));
      Assert.assertEquals(stack.size(), i + 1);
    }

    int maxValue = 0;
    int size = max - 1;

    // 进行数据的获取
    while (maxValue < max) {
      String poValue = stack.popFirst();
      Assert.assertEquals(String.valueOf(maxValue), poValue);
      Assert.assertEquals(size, stack.size());
      maxValue++;
      size--;
    }
  }

  @Test
  public void testRunTask2() {
    int max = 100;

    MyLinkedChromeStack stack = new MyLinkedChromeStack(max);

    // 进行数据放入
    for (int i = 0; i < max; i++) {
      stack.push(String.valueOf(i));
      Assert.assertEquals(stack.size(), i + 1);
    }

    int tmpValue = max - 1;

    // 进行数据获取操作
    while (tmpValue >= 0) {
      String poValue = stack.pop();
      Assert.assertEquals(String.valueOf(tmpValue), poValue);
      tmpValue--;
    }
  }
}
