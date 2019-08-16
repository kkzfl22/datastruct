package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode224;

import java.util.LinkedList;
import java.util.Stack;
import java.util.function.BiFunction;

/**
 * 进行计算器的实现
 *
 * <p>支持括号以及加减乘除法操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class SolutionStack {

  enum Oper {
    ADD('+', (i, j) -> i + j),
    MINUS('-', (i, j) -> i - j),
    MULTIPLICATION('*', (i, j) -> i * j),
    DIVISION('/', (i, j) -> i / j),
    LEFT('(', null),
    RIGHT(')', null);

    private Character oper;

    private BiFunction<Integer, Integer, Integer> count;

    Oper(Character oper, BiFunction<Integer, Integer, Integer> count) {
      this.oper = oper;
      this.count = count;
    }

    /**
     * 进行计算操作
     *
     * @param oper 操作符
     * @param valtmp1 临时值1
     * @param val2 临时值2
     * @return 计算结果
     */
    public static int operCount(Character oper, int valtmp1, int val2) {

      Oper countFun = null;

      for (Oper operitem : values()) {
        if (operitem.oper == oper) {
          countFun = operitem;
          break;
        }
      }

      if (null == countFun) {
        throw new IllegalArgumentException("operation param is error");
      }

      return countFun.count.apply(valtmp1, val2);
    }
  }

  public int calculate(String s) {

    if (s == null || s.isEmpty()) {
      return 0;
    }

    // 存储数值的栈，以及操作符的栈
    Stack<Integer> valueStack = new Stack<>();
    Stack<Character> operStack = new Stack<>();

    char[] dataChars = s.toCharArray();

    char currItem;

    int curIndex = 0;
    while (curIndex < dataChars.length) {
      currItem = dataChars[curIndex];

      // 如果当前数字
      if (currItem >= '0' && currItem <= '9') {
        // 提取连续的数字变为int类型
        int getNums = 0;
        while (curIndex < s.length() && Character.isDigit(dataChars[curIndex])) {
          getNums = getNums * 10 + (dataChars[curIndex] - '0');
          curIndex++;
        }

        valueStack.push(getNums);
        continue;
      }
      // 添加操作符
      else if (currItem == Oper.ADD.oper
          || Oper.MULTIPLICATION.oper == currItem
          || Oper.DIVISION.oper == currItem) {
        operStack.push(currItem);
      } else if (currItem == Oper.MINUS.oper) {
        if (curIndex < s.length() && dataChars[curIndex + 1] == Oper.MINUS.oper) {
          operStack.push(Oper.ADD.oper);
          curIndex++;
        } else {
          operStack.add(Oper.MINUS.oper);
        }
      }
      // 如果当前为左括号进行压栈操作
      else if (currItem == Oper.LEFT.oper) {
        operStack.add(Oper.LEFT.oper);
      }
      // 当出现右括号时，进行出栈操作，将值进行运算
      else if (currItem == Oper.RIGHT.oper) {
        // 1,进行操作数据出栈操作,出栈顺序中需要先将乘法与除法计算完成
        Stack<Integer> tmpCountStack = new Stack<>();
        Stack<Character> tmpOperStack = new Stack<>();
        while (!operStack.isEmpty()) {
          // 检查到左括号时，退出
          if (operStack.peek() == Oper.LEFT.oper) {
            operStack.pop();
            break;
          }
          // 为空弹出两个操作数，一个操作符
          if (tmpCountStack.isEmpty()) {
            tmpCountStack.push(valueStack.pop());
            tmpCountStack.push(valueStack.pop());
            tmpOperStack.push(operStack.pop());
          }
          // 不为空，则弹出一个操作数和一个操作符
          else {
            tmpCountStack.push(valueStack.pop());
            tmpOperStack.push(operStack.pop());
          }
        }

        // 由于两次的出栈入栈操作，导致顺序已经改变，需要重新再执行一次，以返转回正确的执行顺序
        Stack<Integer> tmpCountRunStack = new Stack<>();
        Stack<Character> tmpOperRunStack = new Stack<>();

        while (!tmpCountStack.isEmpty()) {
          tmpCountRunStack.push(tmpCountStack.pop());
        }

        while (!tmpOperStack.isEmpty()) {
          tmpOperRunStack.push(tmpOperStack.pop());
        }

        // 进行临时栈的计算操作
        int countTmp = this.countExpress(tmpCountRunStack, tmpOperRunStack);
        valueStack.push(countTmp);
      }

      curIndex++;
    }

    int countRsp = this.countExpress(valueStack, operStack);

    return countRsp;
  }

  /**
   * 进行乘除法操作
   *
   * @param valueStack
   * @param operStack
   */
  public int countExpress(Stack<Integer> valueStack, Stack<Character> operStack) {
    // 临时的数据栈与操作数栈
    Stack<Integer> tmpValStack = new Stack<>();
    Stack<Character> tmpOperStack = new Stack<>();

    Stack<Integer> tmpCountValStack = new Stack<>();
    Stack<Character> tmpCountOperStack = new Stack<>();

    while (!operStack.isEmpty()) {

      char operTmp = operStack.pop();

      if (operTmp == Oper.MULTIPLICATION.oper || operTmp == Oper.DIVISION.oper) {

        // 将操作数入栈操作
        if (tmpCountOperStack.isEmpty()) {
          tmpCountValStack.push(valueStack.pop());
          tmpCountValStack.push(valueStack.pop());
        } else {
          tmpCountValStack.push(valueStack.pop());
        }

        // 将操作符入栈
        tmpCountOperStack.push(operTmp);

      } else {
        if (!tmpCountOperStack.isEmpty()) {
          // 如果当前已经结束，则检查计算操作数栈是否为空，不为空，则需要进行计算操作
          int counRsp = this.stackCount(tmpCountValStack, tmpCountOperStack);
          // 计算完成，将结果放入到临时栈中
          tmpValStack.push(counRsp);
        } else {
          tmpValStack.push(valueStack.pop());
        }
        tmpOperStack.push(operTmp);
      }
    }

    // 检查数据是否还存在未放入到栈的情况
    if (!valueStack.isEmpty()) {
      tmpValStack.push(valueStack.pop());
    }

    // 检查结果是否为空
    if (!tmpCountValStack.isEmpty()) {
      // 如果当前已经结束，则检查计算操作数栈是否为空，不为空，则需要进行计算操作
      int counRsp = this.stackCount(tmpCountValStack, tmpCountOperStack);
      // 计算完成，将结果放入到临时栈中
      tmpValStack.push(counRsp);
    }

    // 计算最终的结果
    int counRsp = this.stackCount(tmpValStack, tmpOperStack);

    return counRsp;
  }

  /**
   * 进行栈的计算操作
   *
   * @param tmpCountValStack 栈操作数
   * @param tmpCountOperStack 栈数据操作符
   * @return 计算结果
   */
  private int stackCount(Stack<Integer> tmpCountValStack, Stack<Character> tmpCountOperStack) {

    while (!tmpCountOperStack.isEmpty()) {
      int tmpGetval1 = tmpCountValStack.pop();
      int tmpGetval2 = tmpCountValStack.pop();
      char operchar = tmpCountOperStack.pop();
      int coutRsp = Oper.operCount(operchar, tmpGetval1, tmpGetval2);
      tmpCountValStack.push(coutRsp);
    }
    return tmpCountValStack.pop();
  }
}
