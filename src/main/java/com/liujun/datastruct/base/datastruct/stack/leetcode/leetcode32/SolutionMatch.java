package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode32;

import java.util.Stack;

/**
 * 进行最长的有效括号匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/08
 */
public class SolutionMatch {

  private static final char LEFT_PARENTHESS = '(';

  public int longestValidParentheses(String s) {

    if (s == null || s.length() <= 1) {
      return 0;
    }

    Stack<Integer> stack = new Stack<>();
    int count = 0, left = -1;
    for (int i = 0; i < s.length(); i++) {
      // 当为左括号时，压入栈操作
      if (s.charAt(i) == LEFT_PARENTHESS) {
        stack.push(i);
      } else {
        if (!stack.isEmpty()) {
          stack.pop();
          if (!stack.isEmpty()) {
            count = Math.max(count, i - stack.peek());
          } else {
            count = Math.max(count, i - left);
          }
        } else {
          left = i;
        }
      }
    }
    return count;
  }

  /**
   * 连续括号的求解问题
   *
   * @param s
   * @return
   */
  public int parenthess(String s) {
    if (null == s || s.length() < 1) {
      return 0;
    }

    char[] data = s.toCharArray();
    int length = 0;

    Stack<Character> stack = new Stack<>();

    for (int i = 0; i < data.length; i++) {
      // 左括号进行压栈操作
      if (data[i] == LEFT_PARENTHESS) {
        stack.push(data[i]);
      } else {
        // 在匹配上右括号时，弹出一个左括号，长度++
        // 只需要统计左括号，即可*2就可得到总匹配括号数
        if (!stack.isEmpty()) {
          stack.pop();
          length++;
        }
      }
    }

    return length * 2;
  }

  /**
   * 分段连续括号的求解问题
   *
   * @param s
   * @return
   */
  public int parenthessSegment(String s) {
    if (null == s || s.length() < 1) {
      return 0;
    }

    char[] data = s.toCharArray();
    int length = 0;
    int badIndex = -1;

    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < data.length; i++) {
      // 左括号进行压栈操作
      if (data[i] == LEFT_PARENTHESS) {
        stack.push(i);
      } else {
        // 在匹配上右括号时
        if (!stack.isEmpty()) {
          stack.pop();
          // 如果还存在元素，则使用当前索引减栈顶索引
          if (!stack.isEmpty()) {
            length = i - stack.peek();
          }
          // 当前已经为空了，说明需要命名用上次出现不匹配的位置.
          else {
            length = i - badIndex;
          }
        } else {
          badIndex = i;
        }
      }
    }

    return length;
  }

  /**
   * 分段连续括号的求解问题,最终解决
   *
   * @param s
   * @return
   */
  public int parenthessLast(String s) {
    if (null == s || s.length() < 1) {
      return 0;
    }

    char[] data = s.toCharArray();
    int length = 0;
    int badIndex = -1;
    int maxLengthParenthess = 0;

    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < data.length; i++) {
      // 左括号进行压栈操作
      if (data[i] == LEFT_PARENTHESS) {
        stack.push(i);
      } else {
        // 在匹配上右括号时
        if (!stack.isEmpty()) {
          stack.pop();
          // 如果还存在元素，则使用当前索引减栈顶索引
          if (!stack.isEmpty()) {
            length = i - stack.peek();
          }
          // 当前已经为空了，说明需要命名用上次出现不匹配的位置.
          else {
            length = i - badIndex;
          }
          if (length > maxLengthParenthess) {
            maxLengthParenthess = length;
          }
        } else {
          badIndex = i;
        }
      }
    }

    return maxLengthParenthess;
  }
}
