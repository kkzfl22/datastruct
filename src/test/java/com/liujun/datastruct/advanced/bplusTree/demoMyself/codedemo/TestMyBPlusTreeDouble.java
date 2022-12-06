package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 测试B+树
 *
 * @author liujun
 * @since 2022/11/16
 */
public class TestMyBPlusTreeDouble {

  @Test
  public void testBPlusTree() {
    MyBPlusTreeDouble instance = new MyBPlusTreeDouble(5);

    int maxNum = 256;

    for (int i = 0; i < maxNum; i++) {
      instance.insert(i, "v" + i);
    }

    Assertions.assertEquals(instance.get(50), "v50");
    for (int i = 0; i < maxNum; i++) {
      Assertions.assertEquals(instance.get(i), "v" + i);
    }

    // 测试区间查找
    int start = 15;
    int end = 35;
    List<String> rangList = instance.rangeQuery(start, end);
    for (int i = start; i < end; i++) {
      int currIndex = i - start;
      Assertions.assertEquals(rangList.get(currIndex), "v" + i);
    }

    List<String> rangListDesc = instance.rangeQueryDesc(start, end);
    System.out.println(rangListDesc);
    for (int i = start; i < end; i++) {
      int currIndex = i - start;
      Assertions.assertNotNull(rangListDesc.get(currIndex));
    }
  }
}
