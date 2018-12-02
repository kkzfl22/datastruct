package com.liujun.datastruct.heap.solution.midnum;

import org.junit.Test;

/**
 * 进行求中位数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class TestMidNumCount {

  @Test
  public void testMidCount() {
    for (int i = 1; i <= 121; i++) {
      MidNumCount.INSTANCE.putNum(i);
    }
    System.out.println("中位数:" + MidNumCount.INSTANCE.getMidValue());
  }
}
