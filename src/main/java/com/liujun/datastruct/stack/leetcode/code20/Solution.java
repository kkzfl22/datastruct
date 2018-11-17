package com.liujun.datastruct.stack.leetcode.code20;

/**
 * 解决办法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class Solution {

  /** 标识 */
  private static final char FLAG = 'a';

  public class Stack {

    /** 存储空间大小 */
    private char[] arrays;

    /** 容量 */
    private int capacity;

    /** 容量 */
    private int size;

    public Stack(int capacity) {
      this.capacity = capacity;
      this.arrays = new char[capacity];
      this.size = 0;
    }

    /**
     * 放入栈
     *
     * @param val
     */
    public void push(char val) {
      if (size >= capacity) {
        return;
      }
      this.arrays[size] = val;
      this.size++;
    }

    /**
     * 栈顶的元素
     *
     * @return
     */
    public char pop() {
      if (size <= 0) {
        return FLAG;
      }
      size--;
      char val = this.arrays[size];

      return val;
    }
  }

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

    int stackSize = s.length();
    Stack leftStack = new Stack(stackSize);

    BracketEnum bracket = null;

    for (int i = 0; i < braket.length; i++) {
      // 如果为左括号压入左括号左括号栈中
      if (BracketEnum.left(braket[i])) {
        leftStack.push(braket[i]);
      } else {
        // 如果为右括号，则从左侧栈中弹出一个做比较，一至则不放入
        if ((bracket = BracketEnum.right(braket[i])) != null) {
          char left = leftStack.pop();
          if (FLAG == left || left != bracket.left) {
            return false;
          }
        }
      }
    }

    return leftStack.pop() == FLAG;
  }
}
