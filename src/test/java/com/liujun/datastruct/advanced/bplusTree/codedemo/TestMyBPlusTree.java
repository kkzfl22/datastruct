package com.liujun.datastruct.advanced.bplusTree.codedemo;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/28
 */
public class TestMyBPlusTree {

  @Test
  public void testMyBPlusTree() {
    MyBPlusTree instance = new MyBPlusTree();

    instance.insert(1, "a");
    instance.insert(2, "b");
    instance.insert(3, "c");
    instance.insert(4, "d");
    instance.insert(5, "e");
    instance.insert(6, "f");
    instance.insert(7, "g");
    instance.insert(8, "h");
    instance.insert(9, "j");
    instance.insert(10, "i");
  }
}
