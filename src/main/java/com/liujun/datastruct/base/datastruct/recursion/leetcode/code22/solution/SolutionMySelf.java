package com.liujun.datastruct.base.datastruct.recursion.leetcode.code22.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行求括号的最大组合
 *
 * <p>解答错误，当大于3后，会存在更多的情况未考虑的问题。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/24
 */
public class SolutionMySelf {

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

    List<String> countReuslt = new ArrayList<>();

    this.recurstion(0, num, countReuslt, "");

    return countReuslt;
  }

  /**
   * 进行递归的求解
   *
   * @param index 索引号
   * @param maxNum 最大次数
   * @param resut 结果
   */
  public void recurstion(int index, int maxNum, List<String> resut, String msg) {
    if (index == maxNum) {
      if (!resut.contains(msg)) {
        resut.add(msg);
      }
      return;
    }

    // 1,一共分为四种情况，1）括号的左边添加括号，
    msg = PARTHESE_LEFT + PARTHESE_RIGHT + msg;
    recurstion(index + 1, maxNum, resut, msg);
    // 进行字符的还原
    msg = msg.substring(2);

    // 2)在括号的右边添加括号
    msg = msg + PARTHESE_LEFT + PARTHESE_RIGHT;
    recurstion(index + 1, maxNum, resut, msg);
    msg = msg.substring(0, msg.lastIndexOf(PARTHESE_LEFT));

    // 3)在括号的中间添加括号
    String src = new String(msg.getBytes());
    String left = msg.substring(0, msg.length() / 2);
    String right = msg.substring(msg.length() / 2);
    msg = left + PARTHESE_LEFT + PARTHESE_RIGHT + right;
    recurstion(index + 1, maxNum, resut, msg);
    msg = src;

    // 4)在括号的外围再添加括号
    msg = PARTHESE_LEFT + msg + PARTHESE_RIGHT;
    recurstion(index + 1, maxNum, resut, msg);
  }
}
