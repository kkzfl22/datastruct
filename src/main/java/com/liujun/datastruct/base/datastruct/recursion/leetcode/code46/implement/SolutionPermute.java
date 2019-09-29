package com.liujun.datastruct.base.datastruct.recursion.leetcode.code46.implement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给定一个没有重复数字的序列，返回其所有可能的全排列。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/25
 */
public class SolutionPermute {

  public List<List<Integer>> permute(int[] nums) {

    Set<List<Integer>> data = new HashSet<>();
    List<Integer> currdata = new ArrayList<>();

    boolean[] exists = new boolean[nums.length];

    this.recurstion(nums, data, currdata, exists);

    List<List<Integer>> result = new ArrayList<>(data);

    return result;
  }

  private void recurstion(
      int[] nums, Set<List<Integer>> data, List<Integer> currdata, boolean[] exists) {

    // 如果当前组合到达组合的结果大小，则加入到结果集中
    if (currdata.size() == nums.length) {
      data.add(new ArrayList<>(currdata));
      return;
    }

    for (int i = 0; i < nums.length; i++) {
      // 仅对未遍历过元素进行遍历
      if (!exists[i]) {
        exists[i] = true;

        currdata.add(nums[i]);

        this.recurstion(nums, data, currdata, exists);
        currdata.remove(currdata.size() - 1);

        exists[i] = false;
      }
    }
  }
}
