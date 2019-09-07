package com.liujun.datastruct.base.datastruct.stack.leetcode.code20;

/**
 * 解决办法
 *
 * <p>使用数组来实现栈的功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class SolutionArrayStackOther {

  public boolean isValid(String s) {
    if (null == s) {
      return false;
    }

    if (s.length() == 0) {
      return true;
    }

    char[] stack = new char[s.length()];
    int stackIndex = -1;

    char data;
    int readIndex = 0;

    while (readIndex < s.length()) {
      data = s.charAt(readIndex);

      if (data == ' ') {
        readIndex++;
        continue;
      }

      if (data == '(' || data == '{' || data == '[') {
        stack[++stackIndex] = data;
      } else if (data == ')' || data == '}' || data == ']') {
        if (stackIndex >= 0
            && ((stack[stackIndex] == '(' && data == ')')
                || (stack[stackIndex] == '{' && data == '}')
                || (stack[stackIndex] == '[' && data == ']'))) {
          stackIndex--;
        } else {
          return false;
        }
      }

      readIndex++;
    }

    return stackIndex == -1;
  }
}
