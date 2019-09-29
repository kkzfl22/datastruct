package com.liujun.datastruct.base.datastruct.recursion.leetcode.code47.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行全排列的求解
 *
 * <p>这是我首先想到的解决办法，但是这存在问题，
 *
 * <p>问题1，数据中存在很多错误的组合,比如输入[1,2,3]首个就是[1, 1, 1]
 *
 * <p>问题2，重复排列的问题，相同的组合会被多次排列。
 *
 * <p>改进方案
 *
 * <p>思考了很久，没有想出来解决办法。google寻找答案，再来检查
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/20
 */
public class SolutionMySelfSwatch {

  public List<List<Integer>> solution(int[] input) {
    List<List<Integer>> list = new ArrayList<>();
    List<Integer> currList = new ArrayList<>();

    recustion(input, 0, list, currList);

    return list;
  }

  private void recustion(
      int[] input, int start, List<List<Integer>> result, List<Integer> currList) {
    if (currList.size() == input.length) {

      List<Integer> currListTmp = new ArrayList<>();
      for (int currValue : currList) {
        currListTmp.add(currValue);
      }

      result.add(currListTmp);
      return;
    }

    for (int i = 0; i < input.length; i++) {
      if (i < input.length - 1) {
        swapdata(input, i, i + 1);
      }
      currList.add(input[i]);
      recustion(input, start, result, currList);
      currList.remove(currList.size() - 1);
      if (i < input.length - 1) {
        swapdata(input, i + 1, i);
      }
    }
  }

  private void swapdata(int[] array, int startIndex, int outIndex) {
    int value = array[startIndex];
    array[startIndex] = array[outIndex];
    array[outIndex] = value;
  }
}
