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

  @Test
  public void testand() {
    int value = 53;
    int num = 53;
    System.out.println("按拉与操作,参与操作的位中必须全都是 1，那么最终结果才是 1（真），否则就为 0（假）");
    BitOperator.and(value, num);
    System.out.println("------------------------");
    System.out.println("按位或操作，参与操作的位中只要有一个位是 1，那么最终结果就是 1");
    BitOperator.or(value, num);
    System.out.println("------------------------");
    num = 33;
    System.out.println("按位异或操作,它具有排异性，也就是说如果参与操作的位相同，那么最终结果就为 0（假），否则为 1（真）");
    BitOperator.xor(value, num);
  }
}
