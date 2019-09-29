package com.liujun.datastruct.base.datastruct.recursion.leetcode.code40.implement;

import java.util.*;

/**
 * 在存储时使用排序来解决重复存放的问题
 *
 * <p>但存在问题，由于缺少记录下访问元素，同一元素会被多次访问
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/26
 */
public class SolutionCombinationSum2Sort {

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {

    Set<List<Integer>> result = new HashSet<>();
    List<Integer> currcollect = new ArrayList<>();

    this.recurstion(candidates, target, 0, 0, result, currcollect);

    List<List<Integer>> list = new ArrayList<>(result);

    return list;
  }

  private void recurstion(
      int[] candidates,
      int target,
      int currvalue,
      int index,
      Set<List<Integer>> result,
      List<Integer> currcollect) {

    // System.out.println("当前遍历:" + currcollect);

    if (currvalue == target) {
      Collections.sort(currcollect);
      result.add(new ArrayList<>(currcollect));
    }

    if (currvalue > target) {
      return;
    }

    if (index >= candidates.length) {
      return;
    }

    for (int i = index; i < candidates.length; i++) {
      currvalue = currvalue + candidates[i];
      currcollect.add(candidates[i]);
      this.recurstion(candidates, target, currvalue, i, result, currcollect);
      currvalue = currvalue - candidates[i];
      currcollect.remove(currcollect.size() - 1);
    }
  }
}
