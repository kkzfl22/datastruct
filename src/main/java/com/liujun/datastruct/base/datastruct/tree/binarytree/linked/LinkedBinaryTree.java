package com.liujun.datastruct.base.datastruct.tree.binarytree.linked;

/**
 * 基于链表来存储二叉树
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/12
 */
public class LinkedBinaryTree {

  class struct {

    /** 左节点 */
    private struct left;

    /** 当前树存储的值 */
    private int value;

    /** 右节点存储的值 */
    private struct right;

    public struct getLeft() {
      return left;
    }

    public struct getRight() {
      return right;
    }
  }

  /** 基于链表的二叉树存储结构， 此存储根节点 */
  private struct root = new struct();

  public struct getNode() {
    return root;
  }

  public void add(struct node, int value) {
    node.value = value;
    struct left = new struct();
    left.value = 2 * value;
    node.left = left;
    struct right = new struct();
    right.value = 2 * value + 1;
    node.right = right;
  }

  /**
   * 左序遍历
   *
   * 左序遍历的递推公式为
   * preorder(r) = print r -> preorder(r->left) -> preorder(r->right)
   *
   * @param node
   */
  public void leftPrint(struct node) {
    if (node == null) {
      return;
    }
    // 先打印当前，再打印左树，再打印左树
    System.out.println(node.value);
    leftPrint(node.left);
    leftPrint(node.right);
  }

  /**
   * 中序遍历
   *
   * preorder(r) = preorder(r->left) -> print r -> preorder(r->right)
   *
   * @param node
   */
  public void midPrint(struct node) {
    if (null == node) {
      return;
    }

    // 先打印左树，再打印当前，最后打印右边的值
    midPrint(node.left);
    System.out.println(node.value);
    midPrint(node.right);
  }


  /**
   * 后序遍历
   *
   * preorder(r) = preorder(r->left) -> print r -> preorder(r->right)
   *
   * @param node
   */
  public void rightPrint(struct node) {
    if (null == node) {
      return;
    }

    // 先打印左边值，再打印右边，最后打印当前值
    rightPrint(node.left);
    rightPrint(node.right);
    System.out.println(node.value);
  }
}
