package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode150;

import java.util.Stack;
import java.util.function.BiFunction;

/**
 * 逆波兰式的求解问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class EvaluateSolution {

  enum Oper {

    /** 加法操作 */
    ADD("+", (i, j) -> i + j),
    /** 减法操作 */
    MINUS("-", (i, j) -> i - j),
    /** 乘法操作 */
    MULTIPLICATION("*", (i, j) -> i * j),
    /** 除法操作 */
    DIVISION("/", (i, j) -> i / j);

    private String flag;

    private BiFunction<Integer, Integer, Integer> function;

    Oper(String flag, BiFunction<Integer, Integer, Integer> count) {
      this.flag = flag;
      this.function = count;
    }

    public static boolean isOper(String oper) {
      for (Oper item : values()) {
        if (item.flag.equals(oper)) {
          return true;
        }
      }

      return false;
    }

    public static Oper getOper(String oper) {
      for (Oper item : values()) {
        if (item.flag.equals(oper)) {
          return item;
        }
      }

      return null;
    }

    public static int runCount(Oper operation, Integer val1, Integer val2) {
      if (null == operation) {
        return -1;
      }

      return operation.function.apply(val1, val2);
    }
  }
  /**
   * 进行逆波兰式的求解
   *
   * @param operations
   * @return
   */
  public int evalRPN(String[] operations) {

    if (operations.length <= 1) {
      return Integer.parseInt(operations[0]);
    }

    Stack<Integer> valueStack = new Stack<>();

    for (int i = 0; i < operations.length; i++) {

      if (!Oper.isOper(operations[i])) {
        valueStack.push(Integer.parseInt(operations[i]));
      } else {
        // 1,获取操作符
        Oper open = Oper.getOper(operations[i]);
        if (open == null) {
          throw new IllegalArgumentException("operation is not support");
        }

        // 1,弹出操作数1
        int value1 = valueStack.pop();
        int value2 = valueStack.pop();

        int value = Oper.runCount(open, value2, value1);

        // 将结果进行压栈操作
        valueStack.push(value);
      }
    }

    return valueStack.pop();
  }
}
