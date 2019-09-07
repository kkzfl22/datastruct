package com.liujun.datastruct.base.datastruct.stack.leetcode.code20;

import java.util.Stack;

/**
 * 解决方案
 *
 * <p>通过定义枚举进行括号的配对操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class Solution {

  public enum BracketEnum {
    /** 小括号 */
    PARENTHESES('(', ')'),
    /** 中括号 */
    BRACKETS('[', ']'),
    /** 大括号 */
    CURLYBRACES('{', '}');

    private char left;
    private char right;

    BracketEnum(char left, char right) {
      this.left = left;
      this.right = right;
    }

    public static boolean left(char val) {
      for (BracketEnum item : values()) {
        if (item.left == val) {
          return true;
        }
      }
      return false;
    }

    public static BracketEnum right(char val) {
      for (BracketEnum item : values()) {
        if (item.right == val) {
          return item;
        }
      }
      return null;
    }
  }

  public boolean isValid(String s) {
    if (null == s) {
      return false;
    }
    if (s.length() == 0) {
      return true;
    }

    char[] braket = s.toCharArray();
    Stack<Character> leftStack = new Stack();
    BracketEnum bracket = null;

    for (int i = 0; i < braket.length; i++) {
      // 如果为左括号压入左括号左括号栈中
      if (BracketEnum.left(braket[i])) {
        leftStack.push(braket[i]);
      } else {
        // 如果为右括号，则从左侧栈中弹出一个做比较，一至则不放入
        if ((bracket = BracketEnum.right(braket[i])) != null) {
          if (!leftStack.isEmpty()) {
            char left = leftStack.pop();
            if (left != bracket.left) {
              return false;
            }
          } else {
            return false;
          }
        }
      }
    }

    return leftStack.isEmpty();
  }
}
