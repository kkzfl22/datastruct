package com.liujun.datastruct.datastruct.tree.binarytree.linked;

import org.junit.Test;

/**
 * 测试链式二叉树的遍历
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/12
 */
public class TestLinkedBinaryTree {

  private LinkedBinaryTree binaryTree = new LinkedBinaryTree();

  @Test
  public void left() {
    LinkedBinaryTree.struct node = binaryTree.getNode();

    int start = 1;
    binaryTree.add(node, start);
    start = 2;
    binaryTree.add(node.getLeft(), start);
    start = 3;
    binaryTree.add(node.getRight(), start);
    start = 4;
    binaryTree.add(node.getLeft().getLeft(), start);
    start = 5;
    binaryTree.add(node.getLeft().getRight(), start);
    start = 6;
    binaryTree.add(node.getRight().getLeft(), start);
    start = 7;
    binaryTree.add(node.getRight().getRight(), start);

    binaryTree.leftPrint(node);
    System.out.println();
    System.out.println();
    binaryTree.midPrint(node);

    System.out.println();
    System.out.println();
    binaryTree.rightPrint(node);
  }
}
