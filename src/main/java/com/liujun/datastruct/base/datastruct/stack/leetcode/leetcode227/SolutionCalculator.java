package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode227;

import java.util.Stack;

/**
 * 一个简单的非负数的计算器的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/16
 */
public class SolutionCalculator {

  enum OperEnum {
    PLUS('+', 1),
    MINU('-', 1),
    MULT('*', 2),
    DIVI('/', 2),
    ;
    private char oper;
    private int order;

    public static OperEnum getOper(char item) {
      OperEnum oper = null;
      for (OperEnum operitem : values()) {
        if (operitem.oper == item) {
          oper = operitem;
        }
      }

      return oper;
    }

    OperEnum(char oper, int order) {
      this.oper = oper;
      this.order = order;
    }
  }

  public int calculate(String s) {

    if (s == null || s.isEmpty()) {
      return 0;
    }

    // 数值栈
    Stack<Integer> valueStack = new Stack<>();
    // 操作数栈
    Stack<Character> operStack = new Stack<>();

    int curIndex = 0;
    char charItem;
    while (curIndex < s.length()) {
      charItem = s.charAt(curIndex);

      if (charItem >= '0' && charItem <= '9') {
        int currValue = 0;

        //进行数字的提取，比较巧妙。利用的是位之前是10的倍数的关系，然后再加当前数。
        while (curIndex < s.length() && Character.isDigit(s.charAt(curIndex))) {
          currValue = currValue * 10 + (s.charAt(curIndex) - '0');
          curIndex++;
        }
        // 将操作数压栈操作
        valueStack.push(currValue);

        continue;
      } else if (charItem == OperEnum.PLUS.oper
          || charItem == OperEnum.MINU.oper
          || charItem == OperEnum.MULT.oper
          || charItem == OperEnum.DIVI.oper) {

        if (operStack.isEmpty()) {
          operStack.push(charItem);
        } else {
          OperEnum operLast = OperEnum.getOper(operStack.peek());
          OperEnum operCurr = OperEnum.getOper(charItem);
          //算法符的优先级比较
          if (operCurr.order > operLast.order) {
            operStack.push(charItem);
          } else {
            // 执行运算
            runCount(valueStack, operStack, operCurr);
            operStack.push(charItem);
          }
        }
      }

      curIndex++;
    }

    if (!operStack.isEmpty()) {
      runCount(valueStack, operStack, null);
    }

    return valueStack.pop();
  }

  /**
   * 进行栈中数据的计算操作
   * @param valueStack 数据栈
   * @param operStack 操作符栈
   * @param operCurr 当前操作符
   */
  private void runCount(Stack<Integer> valueStack, Stack<Character> operStack, OperEnum operCurr) {
    while (!operStack.isEmpty()) {

      //栈中的操作符优先级低于当前操作符优先级，则不运算。
      OperEnum lastStack = OperEnum.getOper(operStack.peek());
      if (null != operCurr && lastStack.order < operCurr.order) {
        break;
      }

      char currValue = operStack.pop();
      int value2 = valueStack.pop();
      int value1 = valueStack.pop();

      if (currValue == OperEnum.PLUS.oper) {
        valueStack.push(value1 + value2);
      } else if (currValue == OperEnum.MINU.oper) {
        valueStack.push(value1 - value2);
      } else if (currValue == OperEnum.MULT.oper) {
        valueStack.push(value1 * value2);
      } else if (currValue == OperEnum.DIVI.oper) {
        valueStack.push(value1 / value2);
      }
    }
  }
}
