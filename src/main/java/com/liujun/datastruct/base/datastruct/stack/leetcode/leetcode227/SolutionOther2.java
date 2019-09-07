package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode227;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/16
 */
public class SolutionOther2 {

  public int calculate(String s) {

    int[] need = new int[3];

    char op = '+';
    for (char c : s.toCharArray()) {
      if (c == ' ') {
        continue;
      }

      if (c >= '0' && c <= '9') {
        need[2] = need[2] * 10 + (c - '0');
      } else {
        getResult(need, op);
        op = c;
      }
    }

    getResult(need, op);
    return need[0] + need[1];
  }

  public void getResult(int[] need, char op) {
    if (op == '+') {
      need[0] += need[1];
      need[1] = need[2];
    } else if (op == '-') {
      need[0] += need[1];
      need[1] = -need[2];
    } else if (op == '*') {
      need[1] *= need[2];
    } else {
      need[1] /= need[2];
    }
    need[2] = 0;
  }
}
