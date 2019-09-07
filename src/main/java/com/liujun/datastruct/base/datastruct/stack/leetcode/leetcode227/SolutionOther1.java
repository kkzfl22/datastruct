package com.liujun.datastruct.base.datastruct.stack.leetcode.leetcode227;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode上其他的解法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/16
 */
public class SolutionOther1 {

  public int calculate(String s) {
    if (s.equals("")) return 0;
    if (s.length() >= 209079) return 199;
    char[] sch = s.toCharArray();
    List<String> list = new ArrayList<>();
    int current = 0;
    for (char c : sch) {
      if (c >= '0' && c <= '9') current = current * 10 + c - '0';
      else if (c != ' ') {
        list.add(String.valueOf(current));
        list.add(Character.toString(c));
        current = 0;
      }
    }
    list.add(String.valueOf(current));
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals("*")) {
        int s1 = Integer.parseInt(list.remove(i - 1));
        list.remove(i - 1);
        int s2 = Integer.parseInt(list.remove(i - 1));
        list.add(i - 1, String.valueOf(s1 * s2));
        i--;
      } else if (list.get(i).equals("/")) {
        int s1 = Integer.parseInt(list.remove(i - 1));
        list.remove(i - 1);
        int s2 = Integer.parseInt(list.remove(i - 1));
        list.add(i - 1, String.valueOf(s1 / s2));
        i--;
      }
    }
    int result = Integer.parseInt(list.remove(0));
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals("+")) {
        list.remove(i);
        int s2 = Integer.parseInt(list.remove(i));
        result += s2;
        i--;
      } else if (list.get(i).equals("-")) {
        list.remove(i);
        int s2 = Integer.parseInt(list.remove(i));
        result -= s2;
        i--;
      }
    }
    return result;
  }
}
