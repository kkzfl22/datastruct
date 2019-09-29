package com.liujun.datastruct.base.datastruct.recursion.leetcode.code22.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行求括号的最大组合
 *
 * <p>递归求解的过程就在先添加左括号，然后添加右括号
 *
 * <p>递归的终止条件就是当前括号的括号数*2
 *
 * <p>同时右括号必须小于左括号数，因为当右括号数大于左括号数时，不能得到成对的括号，必须要满足
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/24
 */
public class SolutionParthese2 {

  public static final String PARENTHESE_LEFT = "(";
  public static final String PARENTHESE_RIGHT = ")";

  public List<String> generateParenthesis(int num) {
    List<String> result = new ArrayList<>();

    if (num == 1) {
      result.add(PARENTHESE_LEFT + PARENTHESE_RIGHT);
      return result;
    }

    if (num == 0) {
      return result;
    }

    this.recurstion(num, 0, 0, result, "");

    return result;
  }

  /**
   * 递归求最大括号的组合
   *
   * @param maxNum 最大索引号
   * @param left 左索引号
   * @param right 右索引号
   * @param resultLst 存储的结果数
   * @param data 当前已经得到的括号组合
   */
  public void recurstion(int maxNum, int left, int right, List<String> resultLst, String data) {
    // 1,首先写递归的终止条件
    if (data.length() == maxNum * 2) {
      resultLst.add(data);
      return;
    }

    // 添加左括号
    if (left < maxNum) {
      this.recurstion(maxNum, left + 1, right, resultLst, data + PARENTHESE_LEFT);
    }

    // 添加右括号，右括号，需要小于左括号，否则不对成对了
    if (right < left) {
      this.recurstion(maxNum, left, right + 1, resultLst, data + PARENTHESE_RIGHT);
    }
  }
}
