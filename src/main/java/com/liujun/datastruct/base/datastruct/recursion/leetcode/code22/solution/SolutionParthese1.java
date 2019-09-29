package com.liujun.datastruct.base.datastruct.recursion.leetcode.code22.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行求括号的最大组合
 *
 * <p>递归求解的过程就是在左边添加一个括号或者右边添加一个括号，当满足最大限制时，则加入到集合中
 *
 * <p>递归的终止条件就是n=max
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/24
 */
public class SolutionParthese1 {

  public static final String PARTHESE_LEFT = "(";
  public static final String PARTHESE_RIGHT = ")";

  public List<String> generateParenthesis(int num) {
    List<String> result = new ArrayList<>();

    if (num == 0) {
      return result;
    }

    if (num == 1) {
      result.add(PARTHESE_LEFT + PARTHESE_RIGHT);
      return result;
    }

    this.recurstion(num, 0, 0, result, "");

    return result;
  }

  public void recurstion(int maxNum, int left, int right, List<String> resultLst, String data) {

    if (left > maxNum || right > maxNum) {
      return;
    }

    if (left == maxNum && right == maxNum) {
      // 存在重复的问题，所以需要检查下是否存在
      if (!resultLst.contains(data)) {
        resultLst.add(data);
      }
    }

    if (left >= right) {
      String procData = new String(data);

      // 递归的过程就是在左边加一个括号或者在右边添加一个括号
      recurstion(maxNum, left + 1, right, resultLst, data + PARTHESE_LEFT);
      recurstion(maxNum, left, right + 1, resultLst, procData + PARTHESE_RIGHT);
    }
  }
}
