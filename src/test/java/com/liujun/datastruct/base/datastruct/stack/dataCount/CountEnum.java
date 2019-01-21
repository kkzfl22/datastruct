package com.liujun.datastruct.base.datastruct.stack.dataCount;

import java.util.function.BiFunction;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/22
 */
public enum CountEnum {
  ADD("+", 1, (Integer T, Integer S) -> T + S),

  USE("-", 1, (Integer T, Integer S) -> T - S),

  MULT("*", 2, (Integer T, Integer S) -> T * S),

  DIV("/", 2, (Integer T, Integer S) -> T / S),

  SUR("%", 2, (Integer T, Integer S) -> T % S);

  private String flag;

  private int order;

  private BiFunction<Integer, Integer, Integer> methods;

  CountEnum(String flag, int order, BiFunction<Integer, Integer, Integer> oper) {
    this.flag = flag;
    this.order = order;
    this.methods = oper;
  }

  public String getFlag() {
    return flag;
  }

  public int getOrder() {
    return order;
  }

  public static CountEnum getFlag(String flag) {
    for (CountEnum item : values()) {
      if (item.getFlag().equals(flag)) {
        return item;
      }
    }

    return null;
  }

  public static int FunCount(String flag, int value, int value2) {
    CountEnum curr = getFlag(flag);

    if (null != curr) {
      return curr.methods.apply(value, value2);
    }
    return -1;
  }

  /**
   * 检查当前是否为操作符号
   *
   * @param flag 当前读取到的符号
   * @return true 当前为操作符，false 当前蜚操作符
   */
  public static boolean checkFlag(String flag) {
    for (CountEnum item : values()) {
      if (item.getFlag().equals(flag)) {
        return true;
      }
    }

    return false;
  }
}
