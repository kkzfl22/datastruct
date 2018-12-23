package com.liujun.datastruct.datastruct.linkedlist;

import org.junit.Test;

/**
 * 测试LRU缓存
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/30
 */
public class TestCacheLinkedList {

  @Test
  public void testLruCache() {
    CacheLinkedList linkedList = new CacheLinkedList();

    linkedList.putCache(1);
    linkedList.putCache(2);
    linkedList.putCache(3);
    linkedList.putCache(4);
    linkedList.putCache(5);
    linkedList.putCache(6);
    linkedList.putCache(7);
    linkedList.putCache(8);
    linkedList.putCache(9);
    linkedList.putCache(10);

    linkedList.putCache(11);
    linkedList.putCache(12);
    linkedList.putCache(13);
    linkedList.putCache(20);
    linkedList.putCache(21);
    linkedList.putCache(22);
    linkedList.putCache(23);
    linkedList.putCache(24);
    linkedList.putCache(25);

    linkedList.linkedList.printTree(null, 0);
  }
}
