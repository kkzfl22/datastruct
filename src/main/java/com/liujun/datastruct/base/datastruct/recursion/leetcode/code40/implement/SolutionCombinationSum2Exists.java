package com.liujun.datastruct.base.datastruct.recursion.leetcode.code40.implement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用一个数组来记录下已经访问过的元素，优先检查下是否存在于exists数组中，在存储时，先排序再存入入，以防止数同一组数据被重复放入集合中
 *
 * <p>加入首次访问的原因是：在首次时下标从0开始，而后，每次需要在当前下标上加1，以防止重复访问
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/26
 */
public class SolutionCombinationSum2Exists {

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {

    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currcollect = new ArrayList<>();

    // 优先对数据做一次排序
    Arrays.sort(candidates);

    this.recurstion(candidates, target, 0, 0, result, currcollect, true);

    return result;
  }

  /**
   * 使用递归来过量进行问题的求解操作。
   *
   * @param candidates 原始的输入数组
   * @param target 目标数
   * @param currvalue 当前已经累加的值
   * @param index 访问到的索引号
   * @param result 结果记录集合
   * @param currcollect 当前记录下的元素
   * @param first 是否为首次访问
   */
  private void recurstion(
      int[] candidates,
      int target,
      int currvalue,
      int index,
      List<List<Integer>> result,
      List<Integer> currcollect,
      boolean first) {

    if (currvalue == target) {

      List<Integer> data = new ArrayList<>(currcollect);

      if (!result.contains(currcollect)) {
        result.add(data);
      }
    }

    if (index >= candidates.length || currvalue > target) {
      return;
    }

    if (!first) {
      index = index + 1;
    } else {
      first = false;
    }

    for (int i = index; i < candidates.length; i++) {
      currvalue = currvalue + candidates[i];
      currcollect.add(candidates[i]);

      this.recurstion(candidates, target, currvalue, i, result, currcollect, first);
      currvalue = currvalue - candidates[i];
      currcollect.remove(currcollect.size() - 1);
    }
  }
}
