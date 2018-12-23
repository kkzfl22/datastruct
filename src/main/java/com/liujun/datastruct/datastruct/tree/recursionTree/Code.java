package com.liujun.datastruct.datastruct.tree.recursionTree;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/23
 */
public class Code {

  public static int count(int hour) {
    if (hour <= 1) {
      return 1;
    } else if (hour == 2) {
      return 2;
    }
    return (int) Math.pow(2, hour - 1) - (int) Math.pow(2, hour - 3);
  }

  public static int fn(int hour) {
    int value = 1;
    for (int i = 1; i < hour; i++) {
      int currValue = count(hour);
      value = fn(hour - 1) + currValue;
    }

    return value;
  }

  public static void main(String[] args) {
    System.out.println(fn(4));
  }
}
