package com.liujun.math.chapter01Binary;

import java.math.BigInteger;

/**
 * 二进制相当关的内容信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/23
 */
public class Binary {

  /**
   * 将一个十进制数转化为二进制
   *
   * @param decimal
   * @return
   */
  public static String decimalToBinary(int decimal) {
    BigInteger decimalNum = new BigInteger(String.valueOf(decimal));
    return decimalNum.toString(2);
  }

  /**
   * 将一个十进制转化为二进制操作
   *
   * @param decimal 十进制数值
   * @return 转化为的二进制数
   */
  public static String decimalToBinary2(int decimal) {
    return Integer.toBinaryString(decimal);
  }

  /**
   * 将一直二进制数转换为十进制
   *
   * @param binary 二进制数
   * @return 转化的结果
   */
  public static int binaryToDecimal(String binary) {
    BigInteger binaryInt = new BigInteger(String.valueOf(binary), 2);
    return binaryInt.intValue();
  }

  /**
   * 将一个二进制转化为十进制
   *
   * @param binary 二进制内容
   * @return
   */
  public static int binaryToDecimal2(String binary) {
    return Integer.parseInt(binary, 2);
  }
}
