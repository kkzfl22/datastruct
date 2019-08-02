package com.liujun.datastruct.base.datastruct.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 测试动态数组的扩容操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/08
 */
public class TestMyArrayList {

  /** 测试动态数组的在不扩容的情况下，数据添加，删除，与获取 */
  @Test
  public void testDynamicArray() {

    int maxSizeInit = 8;

    int runMaxSize = 16;

    MyArrayList list = new MyArrayList(maxSizeInit);

    for (int i = 0; i < runMaxSize; i++) {
      list.add(i);
      Object valueValues = list.get(i);
      Assert.assertEquals(valueValues, i);
    }

    Assert.assertEquals(runMaxSize, list.size());

    // 进行随机删除操作
    for (int i = 1; i < runMaxSize / 2; i++) {
      list.delete(ThreadLocalRandom.current().nextInt(0, i));
    }

    Assert.assertEquals(runMaxSize / 2 + 1, list.size());

    // 进行设置
    for (int i = 0; i < runMaxSize; i++) {
      int newValue = i * 100 + 1;
      list.set(i, newValue);
      Object newGetValue = list.get(i);
      Assert.assertEquals(newValue, newGetValue);
    }
  }
}
