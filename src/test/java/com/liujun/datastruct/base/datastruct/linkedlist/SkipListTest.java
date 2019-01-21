package com.liujun.datastruct.base.datastruct.linkedlist;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/07
 */
public class SkipListTest {

  private MySkipList skipListInstance = new MySkipList();

  @Test
  public void insert() {
    skipListInstance.insert(1);
    skipListInstance.insert(2);
    skipListInstance.insert(3);
    skipListInstance.insert(4);
    skipListInstance.insert(11);
    skipListInstance.insert(7);
    skipListInstance.insert(5);
    skipListInstance.insert(16);
    skipListInstance.insert(21);
    skipListInstance.insert(18);
    skipListInstance.printTreeNode();
    skipListInstance.insert(25);

    skipListInstance.delete(21);
    skipListInstance.printTreeNode();

    MySkipList.Node finNode = skipListInstance.find(18);
    System.out.println("查询节点:" + finNode);

    skipListInstance.insert(28);
    skipListInstance.insert(30);
    skipListInstance.printTreeNode();
  }
}
