package com.liujun.datastruct.tree.binarySearchTree;

import org.junit.Test;

/**
 * 进行二叉查找树的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/14
 */
public class TestBinarySearchTree {

  private BinarySearchTree binarySearch = new BinarySearchTree();

  @Test
  public void insertFind() {
    binarySearch.insert(8);
    binarySearch.insert(2);
    binarySearch.insert(9);
    binarySearch.insert(3);
    binarySearch.insert(5);
    binarySearch.insert(1);

    binarySearch.printNode(binarySearch.root);
    System.out.println();

    BinarySearchTree.treeNode seachNode = binarySearch.searchNode(3);

    System.out.println(seachNode);
  }
}
