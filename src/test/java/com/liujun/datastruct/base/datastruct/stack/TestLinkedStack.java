package com.liujun.datastruct.base.datastruct.stack;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public class TestLinkedStack {

  @Test
  public void testLinkedStack() {
    LinkedStack linked = new LinkedStack();

    int maxValue = 10;

    for (int i = 0; i < maxValue; i++) {
      linked.push(i);
    }

    int loopIndex = maxValue - 1;

    while (loopIndex > 0) {
      int value = linked.pop();

      Assert.assertEquals(loopIndex, value);

      loopIndex--;
    }
  }
}
