package com.liujun.datastruct.tree.binarySearchTree;

/**
 * 二叉查找树
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/14
 */
public class BinarySearchTree {

  public class treeNode {

    /** 数据信息 */
    private int value;

    /** 左子树 */
    private treeNode left;

    /** 右子树 */
    private treeNode right;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("treeNode{");
      sb.append("value=").append(value);
      sb.append('}');
      return sb.toString();
    }
  }

  /** 根节点树信息 */
  public treeNode root;

  /**
   * 进行二叉查找树的插入操作
   *
   * @param value
   */
  public void insert(int value) {
    // 1,检查当前树节点是否为空，如果为空，则插入根节点
    if (null == root) {
      root = new treeNode();
      root.value = value;
      return;
    }

    treeNode findInser = root;
    // 循环检查
    while (findInser != null) {
      // 1,情况1，如果当前值小于当前值，则插入左子树
      if (value < findInser.value) {
        // 检查左子树是否为空，如果为空，直接插入，
        if (findInser.left == null) {
          treeNode leftNode = new treeNode();
          leftNode.value = value;
          findInser.left = leftNode;
          return;
        }
        // 不为空，则继续遍历
        findInser = findInser.left;
      }

      // 2,如果值大于当前节点，则插入右子树
      else if (value > findInser.value) {
        // 检查右子树是否为空，为空，则直接插入
        if (findInser.right == null) {
          treeNode rightNode = new treeNode();
          rightNode.value = value;
          findInser.right = rightNode;
          return;
        }
        // 不为空，则继续遍历
        findInser = findInser.right;
      }
    }
  }

  /**
   * 二叉查找树的原理，从根节点开始遍历，
   *
   * <p>发现比根节点小的值，就在左边继续查询
   *
   * <p>发现比根节点大的值，就在右边继续查找
   *
   * @param value
   * @return
   */
  public treeNode searchNode(int value) {
    treeNode findNode = root;

    while (findNode != null) {

      if (value == findNode.value) {
        return findNode;
      }

      // 如果值比当前节点小，说明需要在左树继续查找
      else if (value < findNode.value) {
        findNode = findNode.left;
      }
      // 如果值比当前节点大，说明需要在右树继续查找
      else {
        findNode = findNode.right;
      }
    }

    return null;
  }

  /**
   * 使用左序遍历打印二叉树
   *
   * @param node 节点信息
   */
  public void printNode(treeNode node) {

    if (null == node) return;

    System.out.print(node.value + "\t");

    printNode(node.left);
    printNode(node.right);
  }
}
