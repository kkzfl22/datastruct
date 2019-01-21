package com.liujun.datastruct.base.datastruct.stack.bracket;

import com.liujun.datastruct.base.datastruct.stack.ArrayStrStack;

/**
 * 进行括号的检查算法实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/23
 */
public class BracketStrCheck {

  public static void main(String[] args) {
    String checkValue = "[{()}][]";
    ArrayStrStack leftBracket = new ArrayStrStack(10);

    String[] array = checkValue.split("");

    for (int i = 0; i < array.length; i++) {
      if (leftBracket.size() == 0) {
        leftBracket.push(array[i]);
      } else {
        String leftBrack = leftBracket.pop();

        if (!BracketEnum.bracketMatch(leftBrack, array[i])) {
          leftBracket.push(leftBrack);
          leftBracket.push(array[i]);
        }
      }
    }

    System.out.println(leftBracket.size());
  }
}
