package com.liujun.datastruct.base.datastruct.tree.redBlackTree;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/20
 */
public class TestMyRBTree {

  @Test
  public void testBrtree() {
    MyRBTree myRBTree = new MyRBTree();
    myRBTree.insert(33);
    myRBTree.insert(16);
    myRBTree.insert(50);
    myRBTree.insert(13);
    myRBTree.insert(18);
    myRBTree.insert(34);
    myRBTree.insert(58);
    myRBTree.insert(15);
    myRBTree.insert(17);
    myRBTree.insert(25);
    myRBTree.insert(51);
    myRBTree.insert(66);
    myRBTree.insert(19);
    myRBTree.insert(27);
    myRBTree.insert(55);

    this.print(myRBTree.root, myRBTree);

    System.out.println();

    myRBTree.delete(13);
    myRBTree.delete(18);
    myRBTree.delete(55);

    this.print(myRBTree.root, myRBTree);
  }

  private void print(MyRBTree.TreeNode node, MyRBTree myRBTree) {
    Map<Integer, List<MyRBTree.TreeNode>> levelMap = new HashMap<>();
    myRBTree.getLevelTree(node, 1, levelMap);

    for (Map.Entry<Integer, List<MyRBTree.TreeNode>> entry : levelMap.entrySet()) {
      System.out.println(entry);
    }

    System.out.println("------------------------------------------");
    System.out.println();
  }

  @Test
  public void leftCycle() {
    MyRBTree myRBTree = new MyRBTree();
    myRBTree.insert(8);
    myRBTree.insert(4);
    myRBTree.insert(16);
    myRBTree.insert(12);
    myRBTree.insert(20);

    MyRBTree.TreeNode findNode = myRBTree.root.right;
    this.print(myRBTree.root, myRBTree);

    myRBTree.leftCycle(myRBTree.root);

    this.print(findNode, myRBTree);
  }
}
