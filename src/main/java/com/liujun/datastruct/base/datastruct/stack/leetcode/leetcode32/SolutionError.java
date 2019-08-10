package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode32;

import com.liujun.datastruct.base.datastruct.stack.LinkedStack;

/**
 * 进行最长的有效括号匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/08
 */
public class SolutionError {

  private static final char LEFT_PARENTHESS = '(';
  private static final char RIGHT_PARENTHESS = ')';

  public int longestValidParentheses(String s) {

    if (s == null || s.length() <= 1) {
      return 0;
    }

    StringBuilder result = new StringBuilder();

    LinkedStack stackLeft = new LinkedStack();

    char[] data = s.toCharArray();

    for (int i = 0; i < data.length; i++) {
      if (i == 0) {
        stackLeft.push(data[i]);
        continue;
      }
      char valueleft = (char) stackLeft.get();
      char valueright = data[i];

      if (this.isMatch(valueleft, valueright)) {
        char valueleftPop = (char) stackLeft.pop();
        result.append(valueleftPop);
        result.append(valueright);
      } else {
        stackLeft.push(data[i]);
      }
    }

    return result.length();
  }

  private boolean isMatch(char left, char right) {
    if (left == LEFT_PARENTHESS && right == RIGHT_PARENTHESS) {
      return true;
    }
    return false;
  }
}
