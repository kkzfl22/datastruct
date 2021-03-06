package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code652;

import com.config.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用深度优先搜索
 *
 * <p>此方法使用深度优先递归搜索，由于每个子树的序列化值都唯一，可使用这点做深度优先遍历，确定key的次数
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  /** 符号 */
  private static final String SYMBOL = "#";

  public List<TreeNode> findDuplicateSubtrees(TreeNode root) {

    if (null == root) {
      return Collections.emptyList();
    }

    Map<String, Integer> dataMap = new HashMap<>();
    List<TreeNode> result = new ArrayList<>();

    // 执行计算操作
    this.collect(root, dataMap, result);

    return result;
  }

  /**
   * @param node 节点信息
   * @param dataMap 数据的存储的map
   * @param result 结果
   * @return
   */
  private String collect(TreeNode node, Map<String, Integer> dataMap, List<TreeNode> result) {
    if (null == node) {
      return SYMBOL;
    }
    StringBuilder data = new StringBuilder();
    data.append(node.val).append(SYMBOL);
    data.append(this.collect(node.left, dataMap, result));
    data.append(SYMBOL);
    data.append(this.collect(node.right, dataMap, result));
    String key = data.toString();

    dataMap.put(key, dataMap.getOrDefault(key, 0) + 1);
    // 为了防止重复添加，仅在第二次时添加
    if (dataMap.get(key) == 2) {
      result.add(node);
    }
    return key;
  }

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
}
