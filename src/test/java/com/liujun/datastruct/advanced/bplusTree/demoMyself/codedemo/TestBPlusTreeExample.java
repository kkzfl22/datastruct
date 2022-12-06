package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import org.junit.jupiter.api.Test;

/**
 * 进行B+树存储的搜索操作
 *
 * @author liujun
 * @since 2022/11/11
 */
public class TestBPlusTreeExample {

  @Test
  public void base() {
    BPlusTreeMySelf bPlus = new BPlusTreeMySelf(5);

    for (int i = 0; i < 150; i++) {
      System.out.println("插入" + i);
      bPlus.insert(i, "vv" + String.valueOf(i));
    }

    System.out.println(bPlus.query(50));
  }
}
