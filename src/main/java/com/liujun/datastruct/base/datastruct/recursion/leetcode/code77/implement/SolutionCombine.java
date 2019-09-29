package com.liujun.datastruct.base.datastruct.recursion.leetcode.code77.implement;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/27
 */
public class SolutionCombine {

  public List<List<Integer>> combine(int n, int k) {

    List<List<Integer>> list = new ArrayList<>();
    List<Integer> collect = new ArrayList<>();

    boolean[] exists = new boolean[n + 1];

    // 进行递归问题的求解
    this.recurstion(k, 0, n, list, collect, exists);

    return list;
  }

  /**
   * 进行递归的求解K个数的组合
   *
   * @param maxK 最大组合数
   * @param levelIndex 当前层级索引
   * @param dataN 当前数据的最大数
   * @param result 结果集存储
   * @param list 当前存储数据数据集
   * @param exists 标识已经被使用的数据
   */
  public void recurstion(
      int maxK,
      int levelIndex,
      int dataN,
      List<List<Integer>> result,
      List<Integer> list,
      boolean[] exists) {

    if (list.size() == maxK) {
      result.add(new ArrayList<>(list));
    }

    exists[levelIndex] = true;

    for (int i = levelIndex; i <= dataN; i++) {

      list.add(i);

      if (!exists[i]) {

        this.recurstion(maxK, i, dataN, result, list, exists);
      }

      // 递归结果则移除末尾的数据
      list.remove(list.size() - 1);
    }

    exists[levelIndex] = false;
  }
}
