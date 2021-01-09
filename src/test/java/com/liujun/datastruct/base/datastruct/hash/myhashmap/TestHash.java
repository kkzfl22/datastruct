package com.liujun.datastruct.base.datastruct.hash.myhashmap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestHash {

  /**
   * 简单的求余分流算法
   *
   * @param h 当前的唯一HashCode
   * @param length 分流器大小
   * @return 获取分流索引
   * @see [类、类#方法、类#成员]
   */
  public static long indexFor(int h, int length) {
    return h & (length - 1);
  }

  public static long hash2(int h, long length) {
    return h % length;
  }

  private static final long MAX_SIZE = 99999999999l;
  private static final int LENGTH = 512;

  @Test
  public void testAtc() throws InterruptedException {
    System.out.println("...");
    int rsp = 0;
    for (int i = 0; i < 22000000; i++) {
      rsp = i & (LENGTH - 1);
      rsp = i % LENGTH;
    }

    Thread.sleep(1000l);
  }

  @Test
  public void testRunCountIndexFor() {
    long rsp = -1;
    for (long i = 0; i < MAX_SIZE; i++) {
      rsp = i & (LENGTH - 1);
    }
  }

  @Test
  public void runCountHash() {
    long rsp = -1;
    for (long i = 0; i < MAX_SIZE; i++) {
      rsp = i % LENGTH;
    }
  }
}
