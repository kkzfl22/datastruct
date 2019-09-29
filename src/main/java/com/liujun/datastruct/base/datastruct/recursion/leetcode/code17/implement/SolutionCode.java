package com.liujun.datastruct.base.datastruct.recursion.leetcode.code17.implement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/20
 */
public class SolutionCode {

  public List<String> better(String digits) {
    List<String> res = new ArrayList<>();
    String oneRes = "";
    if (digits.equals("")) {
      return res;
    }
    String[] dict = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    int[] digiInt = new int[digits.length()];
    for (int i = 0; i < digits.length(); i++) {
      digiInt[i] = digits.charAt(i) - '0';
    }

    combi(digiInt, 0, dict, res, oneRes);
    return res;
  }

  public void combi(int[] digits, int n, String[] dict, List<String> res, String oneRes) {
    if (n == digits.length) {
      res.add(oneRes);
      return;
    }
    for (int j = 0; j < dict[digits[n]].length(); j++) {
      oneRes = oneRes + dict[digits[n]].charAt(j);
      combi(digits, n + 1, dict, res, oneRes);
      oneRes = oneRes.substring(0, oneRes.length() - 1);
    }
  }
}
