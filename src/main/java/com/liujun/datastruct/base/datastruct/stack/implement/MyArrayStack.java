package com.liujun.datastruct.base.datastruct.stack.implement;

/**
 * 用数组实现一个顺序栈
 *
 * <p>栈，先进后出,后进的先出
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/07
 */
public class MyArrayStack {

  private final int[] stackArray;

  /** 栈的大小 */
  private final int stackSize;

  /** 当前栈的大小 */
  private int currSize;
  /**
   * 进行栈的初始化操作
   *
   * @param stackSize
   */
  public MyArrayStack(int stackSize) {
    this.stackSize = stackSize;
    this.stackArray = new int[stackSize];
  }

  /**
   * 在栈中放入一个数
   *
   * @param value
   */
  public void push(int value) {
    stackArray[currSize++] = value;
  }

  /**
   * 获取当前栈的大小
   *
   * @return
   */
  public int sisze() {
    return currSize;
  }

  /**
   * 从栈中取出一个元素
   *
   * @return 取出的栈顶的元素
   */
  public int pop() {
    int getValue = -1;
    if (currSize > 0) {
      getValue = stackArray[currSize - 1];
    }

    stackArray[currSize - 1] = -1;

    currSize--;

    return getValue;
  }
}
