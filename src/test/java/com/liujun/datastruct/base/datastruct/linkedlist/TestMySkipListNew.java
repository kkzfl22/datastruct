package com.liujun.datastruct.base.datastruct.linkedlist;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/08
 */
public class TestMySkipListNew {

  @Test
  public void skipListTest() {
    MySkipListNew skipInstance = new MySkipListNew();

    for (int i = 0; i < 20; i++) {
      skipInstance.insert(i);
    }

    skipInstance.printTreeNode();

    for (int i = 10; i < 20; i++) {
      System.out.println(skipInstance.findNode(i));
    }

    // 进行删除测试
    for (int i = 19; i >= 0; i--) {
      skipInstance.delete(i);
      skipInstance.printTreeNode();
      System.out.println(
          "-------------------------------------------------------------------------------");
    }

    for (int i = 0; i < 40; i++) {
      skipInstance.insert(i);
    }

    skipInstance.printTreeNode();
  }
}
