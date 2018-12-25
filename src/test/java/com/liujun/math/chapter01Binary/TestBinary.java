package com.liujun.math.chapter01Binary;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/23
 */
public class TestBinary {

  @Test
  public void testToBinary() {
    System.out.println(Binary.decimalToBinary(53));
  }

  @Test
  public void testToBinary2() {
    System.out.println(Binary.decimalToBinary2(53));
  }

  @Test
  public void testToDecimal() {
    System.out.println(Binary.binaryToDecimal("110101"));
    System.out.println(Binary.binaryToDecimal2("110101"));
  }
}
