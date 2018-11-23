package com.liujun.datastruct.tree.redBlackTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 红黑树的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/20
 */
public class MyRBTree {

  /** 树形节点信息 */
  public class TreeNode {

    private int value;

    /** 左子节点 */
    TreeNode left;

    /** 右子节点 */
    TreeNode right;

    /** 父亲节点 */
    TreeNode parent;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("TreeNode{");
      sb.append("value=").append(value);
      sb.append('}');
      return sb.toString();
    }
  }

  public TreeNode root;

  /**
   * 插入操作
   *
   * @param value 插入的值
   */
  public void insert(int value) {
    // 1,检查是否根节点为空，为空直接插根节点
    if (null == root) {
      root = new TreeNode();
      root.value = value;
      return;
    }

    // 1，查找当前插入节点的父节点
    TreeNode findNode = root;

    while (findNode != null) {
      // 如果当前插入的节点比当前节点要小，则在左子树遍历
      if (value < findNode.value) {
        if (null == findNode.left) {
          findNode.left = new TreeNode();
          findNode.left.value = value;
          findNode.left.parent = findNode;
          break;
        }
        findNode = findNode.left;
      }
      // 否则，在右子树遍历
      else {
        if (null == findNode.right) {
          findNode.right = new TreeNode();
          findNode.right.value = value;
          findNode.right.parent = findNode;
          break;
        }
        findNode = findNode.right;
      }
    }

    // 进行平衡调整操作

  }

  /*
   * 对红黑树的节点(x)进行左旋转
   *
   * 左旋示意图(对节点x进行左旋)：
   *      px                              px
   *     /                               /
   *    x                               y
   *   /  \      --(左旋)-.            / \                #
   *  lx   y                         x   ry
   *     /   \                      /  \
   *    ly   ry                    lx  ly
   *
   *
   */
  public void leftCycle(TreeNode x) {

    if (null == x || x.right == null) {
      return;
    }
    // 找点右节点y
    TreeNode y = x.right;

    TreeNode yleft = null;
    if (null != x.left) {
      // 记录下此节点
      yleft = x.left;
    }

    y.left = null;
    y.parent = x.parent;

    x.parent = y;
    x.right = yleft;
  }

  /**
   * 获取所有层级所对应的节点
   *
   * @param node
   * @param index
   * @param levelMap
   */
  public void getLevelTree(TreeNode node, int index, Map<Integer, List<TreeNode>> levelMap) {
    if (null == node) {
      return;
    }

    if (!levelMap.containsKey(index)) {
      levelMap.put(index, new ArrayList<>());
    }

    levelMap.get(index).add(node);

    if (node.left != null) {
      index++;
      getLevelTree(node.left, index, levelMap);
      index--;
    }

    if (node.right != null) {
      index++;
      getLevelTree(node.right, index, levelMap);
      index--;
    }
  }

  /**
   * 进行树形节点的删除操作
   *
   * @param value
   */
  public void delete(int value) {
    // 找到需要删除的节点
    TreeNode findNode = root;
    TreeNode findNodeParent = findNode;

    while (findNode != null) {
      if (value == findNode.value) {
        break;
      }
      // 如果值比当前节点要注，在则左子树查找
      else if (value < findNode.value) {
        findNodeParent = findNode;
        findNode = findNode.left;
      }
      // 否则在右子树查找
      else {
        findNodeParent = findNode;
        findNode = findNode.right;
      }
    }

    // 如果当前可以找到节点则继续操作，找不到，则退出
    if (findNode == null) {
      return;
    }

    // 找到删除的节点可分为3种情况
    // 情况3，左右子树都不为空，查找右子树中最小的值，将右子树中最小值修改为父节点，删除右子树最小节点
    if (findNode.left != null && findNode.right != null) {
      // 查找当前右子树中最小的节点
      TreeNode rightMinNode = findNode.right;
      TreeNode rightMinNodeParent = rightMinNode;

      while (rightMinNode != null) {
        if (rightMinNode.left != null) {
          rightMinNodeParent = rightMinNode;
          rightMinNode = rightMinNode.left;
        } else {
          break;
        }
      }

      // 找到最小节点后
      // 将删除的值的内容替换为右子树中最小节点的值
      findNode.value = rightMinNode.value;

      // 重新处理后续节点的删除操作
      findNodeParent = rightMinNodeParent;
      findNode = rightMinNode;
    }

    // 情况2，左子树不为空右子树为空或者右子树不为空左子树为空，直接将后面节点向上一级
    if (findNode.left != null) {
      // 如果待删除的节点在左边，则进行左子树修改
      if (findNodeParent.left == findNode) {
        findNodeParent.left = findNode.left;
      }
      // 在右子树，进行右子树的修改
      else if (findNodeParent.right == findNode) {
        findNodeParent.right = findNode.left;
      }
    } else if (findNode.right != null) {
      // 如果待删除的节点在左边，则进行左子树修改
      if (findNodeParent.left == findNode) {
        findNodeParent.left = findNode.right;
      }
      // 在右子树，进行右子树的修改
      else if (findNodeParent.right == findNode) {
        findNodeParent.right = findNode.right;
      }
    }

    // 情况1，左右子树都为空，直接删除节点
    if (findNode.left == null && findNode.right == null) {
      if (findNodeParent.left == findNode) {
        findNodeParent.left = null;
      } else if (findNodeParent.right == findNode) {
        findNodeParent.right = null;
      }
    }
  }
}
