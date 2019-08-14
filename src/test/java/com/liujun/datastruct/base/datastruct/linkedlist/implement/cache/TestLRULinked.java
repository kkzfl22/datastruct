package com.liujun.datastruct.base.datastruct.linkedlist.implement.cache;

import org.junit.Test;

/**
 * 测试缓存的lru实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/10
 */
public class TestLRULinked {

  @Test
  public void testlru() {
    int max = 10;
    LRULinked instance = new LRULinked(max);

    for (int i = 0; i < max; i++) {
      instance.push(i);
    }

    // 进行头节点的弹出
    instance.popHead();
    instance.push(12);
  }
}
