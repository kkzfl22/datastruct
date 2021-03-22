package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code652;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution2 {

  @Test
  public void findDuplicateSubtrees() {
    Solution2.TreeNode node = new Solution2.TreeNode(2);
    node.left = new Solution2.TreeNode(2);
    node.right = new Solution2.TreeNode(3);
    node.left.left = new Solution2.TreeNode(4);
    node.right.left = new Solution2.TreeNode(2);
    node.right.right = new Solution2.TreeNode(4);
    node.right.left.left = new Solution2.TreeNode(4);
    // 数据重复检查
    this.assertCheck(node, 2);
  }

  @Test
  public void findDuplicateSubtrees2() {
    Solution2.TreeNode node = new Solution2.TreeNode(2);
    node.left = new Solution2.TreeNode(1);
    node.right = new Solution2.TreeNode(1);

    // 数据重复检查
    this.assertCheck(node, 1);
  }

  @Test
  public void findDuplicateSubtrees3() {
    Solution2.TreeNode node = new Solution2.TreeNode(2);
    node.left = new Solution2.TreeNode(2);
    node.right = new Solution2.TreeNode(2);
    node.left.left = new Solution2.TreeNode(3);
    node.right.left = new Solution2.TreeNode(3);

    // 数据重复检查
    this.assertCheck(node, 2);
  }

  /**
   * 数据检查
   *
   * @param node 根节点
   * @param assertNum
   */
  private void assertCheck(Solution2.TreeNode node, int assertNum) {
    Solution2 solution = new Solution2();
    List<Solution2.TreeNode> dataList = solution.findDuplicateSubtrees(node);

    Assert.assertEquals(dataList.size(), assertNum);
  }
}
