package com.liujun.datastruct.datastruct.hash.cache.lru;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/09
 */
public class TestCacheLRU {

  @Test
  public void lruCache() {
    CacheLRU lru = new CacheLRU(8, 8);

    String msg;
    for (int i = 0; i < 32; i++) {
      lru.put(String.valueOf(i), String.valueOf(i));
    }

    for (int i = 1; i < 5; i++) {
      lru.put(String.valueOf(i), String.valueOf(i));
    }

    // System.out.println("当前大小:" + lru.size());

    System.out.println("-----------------------------------------");

    System.out.println("获取数据:" + lru.get("3"));

    lru.printHashTreeNode();
    System.out.println("-----------------------------------------");
    lru.printLinkedTreeNode();
    System.out.println();
    System.out.println();
    System.out.println("-----删除-----------------------------------------");
    System.out.println();
    lru.delete("4");
    lru.printHashTreeNode();
    lru.printLinkedTreeNode();

    System.out.println("-----删除-----------------------------------------");
    System.out.println();
    lru.delete("3");
    lru.printHashTreeNode();
    lru.printLinkedTreeNode();
  }
}
