package com.liujun.datastruct.advanced.bloomfilter;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/15
 */
public class TestBloomFilter {

  private BloomFilter filter = new BloomFilter();

  @Test
  public void testUrl1() {
    for (int i = 0; i < 100; i++) {
      String url = "https://time.geekbang.org/column/article/76827" + i;

      boolean exists = filter.exists(url);
      System.out.println("当前是否存在:" + exists);
      filter.putUrl(url);
      System.out.println("放入完成");
      boolean exists2 = filter.exists(url);
      System.out.println("当前是否存在:" + exists2);

      System.out.println("-----------------------------------");
    }
  }
}
