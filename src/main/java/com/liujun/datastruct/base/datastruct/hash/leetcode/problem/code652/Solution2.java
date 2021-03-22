package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code652;

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
public class Solution2 {

  private int t = 1;

  /** 符号 */
  private static final String SYMBOL = "#";

  public List<TreeNode> findDuplicateSubtrees(TreeNode root) {

    if (null == root) {
      return Collections.emptyList();
    }

    Map<String, Integer> dataMap = new HashMap<>();
    Map<Integer, Integer> catchValue = new HashMap<>();
    List<TreeNode> result = new ArrayList<>();

    // 执行计算操作
    this.collect(root, dataMap, catchValue, result);

    return result;
  }

  /**
   * @param node 节点信息
   * @param dataSaveMap 数据的存储的map
   * @param result 结果
   * @return
   */
  private int collect(
      TreeNode node,
      Map<String, Integer> dataSaveMap,
      Map<Integer, Integer> catchValue,
      List<TreeNode> result) {

    if (node == null) {
      return 0;
    }

    StringBuilder data = new StringBuilder();
    data.append(node.val).append(SYMBOL);
    data.append(this.collect(node.left, dataSaveMap, catchValue, result));
    data.append(SYMBOL);
    data.append(this.collect(node.right, dataSaveMap, catchValue, result));
    String key = data.toString();
    // 当key不存在时，执行添加方法,然后返回，存在时，获取返回
    int uid = dataSaveMap.computeIfAbsent(key, x -> t++);
    // 树的唯一的id
    catchValue.put(uid, catchValue.getOrDefault(uid, 0) + 1);

    if (catchValue.get(uid) == 2) {
      result.add(node);
    }

    return uid;
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
