package com.liujun.datastruct.base.datastruct.array;

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
  public void testExtend1() {

    int maxSize = 8;

    MyArrayList list = new MyArrayList(maxSize);

    for (int i = 0; i < maxSize; i++) {
      list.add(i);
    }

    // 进行随机删除操作
    for (int i = 0; i < maxSize / 2; i++) {
      list.delete(ThreadLocalRandom.current().nextInt(0, i));
    }

    // 进行设置
    for (int i = 0; i < maxSize; i++) {
      list.set(i, i * 100 + 1);
    }

    for (int i = 0; i < maxSize; i++) {
      list.get(i);
    }
  }
}
