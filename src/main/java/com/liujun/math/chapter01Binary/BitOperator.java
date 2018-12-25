package com.liujun.math.chapter01Binary;

/**
 * 位操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/23
 */
public class BitOperator {

  /**
   * 进行数据左移操作
   *
   * <p>向左移位，即每移动一位即乘2
   *
   * @param num 数值,待移位的十进制数
   * @param m 左移的位数
   * @return 结果
   */
  public static int leftShift(int num, int m) {
    return num << m;
  }

  /**
   * 进行数据的逻辑右移位操作
   *
   * <p>逻辑右移表示，符号位会改变，其他位向右移动
   *
   * <p>向右移位，即每次除2操作
   *
   * @param num 数据信息
   * @param m 移动的位数
   * @return 结果
   */
  public static int rightShift(int num, int m) {
    return num >>> m;
  }

  /**
   * 进行数据的算术右移位操作
   *
   * <p>逻辑右移表示，符号位不改变，其他位向右移动
   *
   * <p>向右移位，即每次除2操作
   *
   * @param num 数据信息
   * @param m 移动的位数
   * @return 结果
   */
  public static int rightShift2(int num, int m) {
    return num >> m;
  }

  /**
   * 进行按位或操作
   *
   * @param num1 数值1
   * @param num2 数值2
   * @return 结果
   */
  public static int or(int num1, int num2) {
    System.out.println("num1的二进制:" + Binary.decimalToBinary(num1));
    System.out.println("num2的二进制:" + Binary.decimalToBinary(num2));

    return num1 | num2;
  }
}
