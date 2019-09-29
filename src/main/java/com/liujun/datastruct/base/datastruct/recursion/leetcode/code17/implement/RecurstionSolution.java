package com.liujun.datastruct.base.datastruct.recursion.leetcode.code17.implement;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用递归来实现
 *
 * <p>要求解的问题是找出所有的组合。
 *
 * <p>将问题分解为子问题:
 *
 * <p>求解思路：即将数字所在的下标中的每个字母与另外一个字符中的字符进行组合。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/19
 */
public class RecurstionSolution {

  private static final String[] STAT_CODE =
      new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

  public List<String> letterCombinations(String digits) {

    List<String> result = new ArrayList<>();
    if (digits == null || digits.isEmpty()) {
      return result;
    }

    String inputData = digits.trim();
    // 1,将输入的字符转化为数字
    int[] dataNum = new int[inputData.length()];

    for (int i = 0; i < inputData.length(); i++) {
      dataNum[i] = inputData.charAt(i) - '0';
    }

    String dataChar = "";

    this.recurstionData(dataNum, 0, result, dataChar);
    return result;
  }

  /**
   * 进行递归的求解
   *
   * @param dataNum 输入字符
   * @param index 索引号
   * @param list 数据集合
   * @param dataChar 当前的字符
   */
  private void recurstionData(int[] dataNum, int index, List<String> list, String dataChar) {
    // 1，递归的终止条件
    if (index == dataNum.length) {
      list.add(new String(dataChar));
      return;
    }

    // 进行递归的求解
    for (int i = 0; i < STAT_CODE[dataNum[index]].length(); i++) {
      dataChar = dataChar + STAT_CODE[dataNum[index]].charAt(i);
      // 当取出一个字符后继续进行遍历
      this.recurstionData(dataNum, index + 1, list, dataChar);
      // 当遍历完成后，需要删除当前的最后一个字符,以可以与其他进行组合
      dataChar = dataChar.substring(0, dataChar.length() - 1);
    }
  }
}
