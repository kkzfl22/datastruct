package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode224;

import java.util.Stack;

/**
 * 进行简单计算器的实现,直接计算操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class SolutionCount {

  public int calculate(String s) {

    if (s == null || s.isEmpty()) {
      return 0;
    }

    Stack<Integer> countStack = new Stack<>();

    char[] dataChars = s.toCharArray();

    char currItem;
    int operSign = 1;
    int operRsp = 0;

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
        // 由于只涉及加减法，可以将加减法转换为加法操作
        operRsp = operRsp + getNums * operSign;
        continue;
      } else if (currItem == '+') {
        operSign = 1;
      } else if (currItem == '-') {
        operSign = -1;
      }
      // 如果当前为左括号进行压栈操作
      else if (currItem == '(') {
        countStack.push(operRsp);
        operRsp = 0;
        // 再将操作符放入栈中
        countStack.push(operSign);
        // 重置操作符
        operSign = 1;
      }
      // 当出现右括号时，进行出栈操作，将值进行运算
      else if (currItem == ')') {
        operSign = countStack.pop();
        int tmpValue = countStack.pop();
        operRsp = tmpValue + operSign * operRsp;
      }

      curIndex++;
    }

    return operRsp;
  }
}
