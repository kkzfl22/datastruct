package com.liujun.math.chapter01Binary;

import org.junit.Test;

import java.math.BigInteger;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/23
 */
public class TestBitOperator {

  @Test
  public void testLeftShift() {
    int value = -53;
    int num = 1;
    int outValus = BitOperator.leftShift(value, num);
    System.out.println("负数向左移位的结果" + outValus);
    int outvalue2 = BitOperator.rightShift(value, num);
    System.out.println("负数逻辑向右移位的结果:" + outvalue2);

    int outvalue3 = BitOperator.rightShift2(value, num);
    System.out.println("负数算术向右移位的结果:" + outvalue3);
  }

  @Test
  public void testnum2() {
    int value = 53;
    int num = 1;
    int outValus = BitOperator.leftShift(value, num);
    System.out.println("正数向左移位的结果" + outValus);
    int outvalue2 = BitOperator.rightShift(value, num);
    System.out.println("正数逻辑向右移位的结果:" + outvalue2);

    int outvalue3 = BitOperator.rightShift2(value, num);
    System.out.println("正数算术向右移位的结果:" + outvalue3);
  }
}
