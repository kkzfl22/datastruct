package com.liujun.datastruct.base.datastruct.recursion.leetcode.code47.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 进行全排列的求解
 *
 * <p>这是我首先想到的解决办法，但是这存在问题，
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/20
 */
public class SolutionMySelf {

  public List<List<Integer>> permuteUnique(int[] input) {
    Set<List<Integer>> list = new HashSet<>();
    List<Integer> currList = new ArrayList<>();
    boolean[] exists = new boolean[input.length];

    recustion(input, list, currList, exists);

    List<List<Integer>> result = new ArrayList<>(list);

    return result;
  }

  /**
   * 进行递归的求解
   *
   * @param input 输入
   * @param result 结果集
   * @param currList 当前的数据集
   * @param exists 它的作用是每个只访问一次，不能重复访问
   */
  private void recustion(
      int[] input, Set<List<Integer>> result, List<Integer> currList, boolean[] exists) {
    // 1,检查是否已经满足条件
    if (currList.size() == input.length) {
      result.add(new ArrayList<>(currList));
      return;
    }

    // 开始遍历操作
    for (int i = 0; i < input.length; i++) {
      // 通过exists则可以让每个元素仅访问一次
      if (exists[i] == false) {
        exists[i] = true;
        currList.add(input[i]);
        recustion(input, result, currList, exists);
        currList.remove(currList.size() - 1);
        exists[i] = false;
      }
    }
  }
}
