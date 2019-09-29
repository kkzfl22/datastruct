package com.liujun.datastruct.base.datastruct.recursion.leetcode.code40.implement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * leetcode上大神的解法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/28
 */
public class SolutionCombinationSum2LeetCode {

  List<List<Integer>> res = new ArrayList<>();

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {

    Arrays.sort(candidates);

    helper(candidates, 0, target, new ArrayList<>());

    return res;
  }

  /**
   * 放假宪递归的遍历求解
   *
   * @param nums 当前求解的原始数组
   * @param start 开始索引号
   * @param target 目标数
   * @param prev 当前已经记录下的结果集
   */
  public void helper(int[] nums, int start, int target, List<Integer> prev) {

    // 当小于0说明超出了，直接退出此次递归
    if (target < 0) return;

    // 等于0说明此次满足了目标数，则记录下此组合
    if (target == 0) {
      res.add(new ArrayList<>(prev));
      return;
    }

    // 从指定的位置开始遍历
    for (int i = start; i < nums.length; i++) {
      // 获取当前值
      int curr = nums[i];

      // 如果当前数大于目标数，则退出，进行减枝操作
      if (curr > target) {
        break;
      }

      // 如果当前索引大于开始值，并且当前值与前一个数相同，则跳过
      // 当此条件去掉会出现重复数据
      // 在有重复数据情况下，此有作用
      if (i > start && curr == nums[i - 1]) {
        System.out.println("出现的数:" + prev);
        System.out.println("满足条件");
        continue;
      }

      // 加入到目标集合中
      prev.add(curr);
      // 进行递归的遍历操作
      helper(nums, i + 1, target - curr, prev);
      // 完成，移除加入的最后一个数
      prev.remove(prev.size() - 1);
    }

    return;
  }
}
