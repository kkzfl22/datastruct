package com.liujun.datastruct.base.datastruct.tree.binarySearchTree;

import org.junit.Test;

/**
 * 进行二叉查找树的操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/14
 */
public class TestBinarySearchTree {

  @Test
  public void insertFind() {

    BinarySearchTree binarySearch = new BinarySearchTree();

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

    binarySearch.printTreeeNode(binarySearch.root);
  }

  @Test
  public void printLevel() {

    BinarySearchTree binarySearch = new BinarySearchTree();

    binarySearch.insert(5);
    binarySearch.insert(1);
    binarySearch.insert(2);
    binarySearch.insert(3);
    binarySearch.insert(4);
    binarySearch.insert(6);
    binarySearch.insert(7);
    binarySearch.insert(8);
    binarySearch.insert(9);

    binarySearch.printNode(binarySearch.root);

    binarySearch.printTreeeNode(binarySearch.root);
  }

  @Test
  public void delete() {

    BinarySearchTree binarySearch = new BinarySearchTree();

    binarySearch.insert(33);
    binarySearch.insert(16);
    binarySearch.insert(50);
    binarySearch.insert(13);
    binarySearch.insert(18);
    binarySearch.insert(34);
    binarySearch.insert(58);
    binarySearch.insert(15);
    binarySearch.insert(17);
    binarySearch.insert(25);
    binarySearch.insert(51);
    binarySearch.insert(66);
    binarySearch.insert(19);
    binarySearch.insert(27);
    binarySearch.insert(55);

    BinarySearchTree.treeNode seachNode = binarySearch.searchNode(55);
    System.out.println(seachNode);

    System.out.println("删除开始");
    System.out.println("----------------------------------------------");

    binarySearch.delete(13);

    binarySearch.printTreeeNode(binarySearch.root);
    System.out.println("----------------------------------------------");

    binarySearch.delete(18);

    binarySearch.printTreeeNode(binarySearch.root);
    System.out.println("----------------------------------------------");
    binarySearch.delete(55);

    binarySearch.printTreeeNode(binarySearch.root);
    System.out.println("----------------------------------------------");
  }

  @Test
  public void delete2() {

    BinarySearchTree binarySearch = new BinarySearchTree();

    binarySearch.insert(33);
    binarySearch.insert(16);
    binarySearch.insert(50);
    binarySearch.insert(13);
    binarySearch.insert(18);
    binarySearch.insert(34);
    binarySearch.insert(58);
    binarySearch.insert(15);
    binarySearch.insert(17);
    binarySearch.insert(25);
    binarySearch.insert(51);
    binarySearch.insert(66);
    binarySearch.insert(19);
    binarySearch.insert(27);
    binarySearch.insert(55);

    BinarySearchTree.treeNode seachNode = binarySearch.searchNode(55);
    System.out.println(seachNode);

    System.out.println("删除开始");
    System.out.println("----------------------------------------------");

    binarySearch.delete2(13);

    binarySearch.printTreeeNode(binarySearch.root);
    System.out.println("----------------------------------------------");

    binarySearch.delete2(18);

    binarySearch.printTreeeNode(binarySearch.root);
    System.out.println("----------------------------------------------");
    binarySearch.delete2(55);

    binarySearch.printTreeeNode(binarySearch.root);
    System.out.println("----------------------------------------------");

    int maxLevel = binarySearch.getBinaryLevel(binarySearch.root, 0);
    System.out.println("计算层级的代码为:" + maxLevel);
  }
}
