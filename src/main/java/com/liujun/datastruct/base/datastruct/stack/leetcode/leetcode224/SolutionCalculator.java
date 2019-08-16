package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode224;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 进行一个计算器的实现
 *
 * <p>失败的实现
 *
 * <p>1,问题对于提取数字太过复杂
 *
 * <p>2,处理不了负数的数字
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class SolutionCalculator {

  private static final String PARENTHESE_LEFT = "(";

  private static final String PARENTHESE_RIGHT = ")";

  private static final String OPER_ADD = "+";

  private static final String OPER_MINUS = "-";

  public int calculate(String s) {

    // 1,检查当前是否存在(号
    if (s == null || s.length() < 1) {
      return 0;
    }

    // 检查是否存在括号，如果存在括号，则使用括号的运算
    if (s.indexOf(PARENTHESE_LEFT) != -1) {
      LinkedList<Character> stack = new LinkedList<>();

      char[] data = s.toCharArray();

      for (int i = 0; i < data.length; i++) {
        if (data[i] != ')') {
          stack.push(data[i]);
        }
        // 如果当前为右括号则进行出栈操作
        else {
          countStack(stack);
        }
      }

      return countStack(stack);

    }
    // 不存在括号，则直接运算
    else {

      String[] dataArray = dataSpit(s);

      return this.count(dataArray);
    }
  }

  private int countStack(LinkedList<Character> stack) {
    StringBuilder item = new StringBuilder();
    while (!stack.isEmpty()) {
      char value = stack.pop();
      if (value == '(') {
        break;
      } else {
        item.append(value);
      }
    }

    // 进行反转
    item.reverse();

    // 进行计算操作
    String[] dataArray = dataSpit(item.toString());

    // 计算括号中的值
    int value = this.count(dataArray);

    // 进行压栈操作
    char[] tochars = String.valueOf(value).toCharArray();
    for (char charItem : tochars) {
      stack.push(charItem);
    }

    return value;
  }

  public String[] dataSpit(String s) {
    List<String> data = new ArrayList<>();
    int findIndex = 0;
    int leftIndex;
    int rightIndex;
    int minVal;

    while (findIndex < s.length()) {
      leftIndex = s.indexOf(OPER_ADD, findIndex);
      rightIndex = s.indexOf(OPER_MINUS, findIndex);

      // 在同时能找到符号时，取最小的符号
      if (leftIndex != -1 && rightIndex != -1) {
        minVal = Math.min(leftIndex, rightIndex);

        if (minVal == leftIndex) {
          data.add(s.substring(findIndex, leftIndex));
          data.add(OPER_ADD);
          findIndex = leftIndex + 1;
          continue;
        } else if (minVal == rightIndex) {
          data.add(s.substring(findIndex, rightIndex));
          data.add(OPER_MINUS);
          findIndex = rightIndex + 1;
          continue;
        }
      }

      // 只找到加法
      if (leftIndex != -1) {
        data.add(s.substring(findIndex, leftIndex));
        data.add(OPER_ADD);
        findIndex = leftIndex + 1;
        continue;
      }

      // 只找到减法
      if (rightIndex != -1) {
        data.add(s.substring(findIndex, rightIndex));
        data.add(OPER_MINUS);
        findIndex = rightIndex + 1;
        continue;
      }

      if (leftIndex == -1 && rightIndex == -1) {
        break;
      }
    }

    data.add(s.substring(findIndex));

    String[] reslut = new String[data.size()];

    for (int i = 0; i < data.size(); i++) {
      reslut[i] = data.get(i);
    }

    return reslut;
  }

  private int count(String[] array) {

    LinkedList<String> valueStack = new LinkedList<>();

    int val1;
    int val2;
    int valRsp;

    for (int i = 0; i < array.length; i++) {
      if (valueStack.size() < 2) {
        valueStack.push(array[i].trim());
      } else {
        String oper = valueStack.pop();
        String valStr1 = valueStack.pop();

        switch (oper) {
          case OPER_ADD:
            val1 = Integer.parseInt(valStr1.trim());
            val2 = Integer.parseInt(array[i].trim());
            valRsp = val1 + val2;
            valueStack.push(String.valueOf(valRsp));
            break;
          case OPER_MINUS:
            val1 = Integer.parseInt(valStr1.trim());
            val2 = Integer.parseInt(array[i].trim());
            valRsp = val1 - val2;
            valueStack.push(String.valueOf(valRsp));
            break;
        }
      }
    }

    int rsp = Integer.parseInt(valueStack.pop());

    return rsp;
  }
}
