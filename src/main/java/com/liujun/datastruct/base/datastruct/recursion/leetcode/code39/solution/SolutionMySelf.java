package com.liujun.datastruct.base.datastruct.recursion.leetcode.code39.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 找出目标数
 *
 * <p>使用递归来进行求解,找出数组中所有能满足目录数的组合
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/20
 */
public class SolutionMySelf {

  public List<List<Integer>> combinationSum(int[] inputSrc, int target) {

    if (inputSrc == null || inputSrc.length == 0) {
      return null;
    }

    List<Integer> currList = new ArrayList<>();
    Set<List<Integer>> collect = new HashSet<>();

    this.recurstion(inputSrc, target, 0, 0, currList, collect);

    List<List<Integer>> result = new ArrayList<>(collect);

    return result;
  }

  /**
   * 进行问题的递归求解,对于重复的数据项，即通过起始值来操作，当已经遍历过的数据，将不再遍历。
   *
   * @param inputSrc 输入数组
   * @param target 目标值
   * @param value 当前的值
   * @param startIndex 开始的索引号，小技巧，这样可以避免重复的计算。只需要从当前向后推进即可
   * @param collect 当前已经记录的集合
   * @param resultList 记录下已经求解的数据集
   */
  private void recurstion(
      int[] inputSrc,
      int target,
      int value,
      int startIndex,
      List<Integer> collect,
      Set<List<Integer>> resultList) {

    // 1,递归的终止条件
    if (value == target) {
      resultList.add(new ArrayList<>(collect));
      return;
    }
    // 2,超过出说明不符合直接退出
    if (value > target) {
      return;
    }

    for (int i = startIndex; i < inputSrc.length; i++) {
      value = value + inputSrc[i];
      collect.add(inputSrc[i]);
      this.recurstion(inputSrc, target, value, i, collect, resultList);
      // 当结束后，需要移除最后一个元素
      collect.remove(collect.size() - 1);
      value = value - inputSrc[i];
    }
  }
}
