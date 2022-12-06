package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import com.liujun.datastruct.advanced.bplusTree.demoMyself.MyBPlusTreeBase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/28
 */
public class TestMyBPlusTreeBase {

  @Test
  public void testMyBPlusTree() {
    MyBPlusTreeBase instance = new MyBPlusTreeBase();

    int maxNum = 128;

    for (int i = 0; i < maxNum; i++) {
      instance.insert(i, "v" + i);
    }

    Assertions.assertEquals(instance.get(50), "v50");
    for (int i = 0; i < maxNum; i++) {
      Assertions.assertEquals(instance.get(i), "v" + i);
    }

    // 测试区间查找
    int start = 30;
    int end = 300;
    List<String> rangList = instance.rangQuery(start, end);
    for (int i = start; i < end; i++) {
      int currIndex = i - start;
      Assertions.assertEquals(rangList.get(currIndex), "v" + i);
    }
  }
}
