package com.liujun.datastruct.base.datastruct.tree.binarytree.order;

import org.junit.Test;

/**
 * 进行数组的二叉树遍历
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/12
 */
public class TestArrayBinaryTree {

  @Test
  public void arrayBinaryTree() {

    ArrayBinaryTree binaryTree = new ArrayBinaryTree();

    binaryTree.putRoot();

    binaryTree.leftPrint(1);
    binaryTree.midPrint(1);
    binaryTree.rightPrint(1);
  }
}
