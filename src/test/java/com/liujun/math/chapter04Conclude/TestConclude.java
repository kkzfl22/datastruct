package com.liujun.math.chapter04Conclude;

import org.junit.Test;

/**
 * 进行数学归纳法的证明，通过程序的方式
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestConclude {

  @Test
  public void testConclude() {
    Conclude instance = new Conclude();

    boolean rsp = instance.prove(63, instance.getInstance());

    System.out.println("结果:" + rsp);
  }
}
