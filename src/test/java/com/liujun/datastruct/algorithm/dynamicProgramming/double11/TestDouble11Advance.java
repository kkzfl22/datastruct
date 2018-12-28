package com.liujun.datastruct.algorithm.dynamicProgramming.double11;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/28
 */
public class TestDouble11Advance {

  @Test
  public void testDouble11() {
    int[] price = new int[] {58, 68, 78, 45, 49, 88, 128};
    int num = price.length;
    int maxValue = 200;
    Double11Advance instance = new Double11Advance();
    instance.double11(price, num, maxValue);
  }
}
