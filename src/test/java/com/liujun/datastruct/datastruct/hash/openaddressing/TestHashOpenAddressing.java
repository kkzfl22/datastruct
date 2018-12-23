package com.liujun.datastruct.datastruct.hash.openaddressing;

import org.junit.Test;

/**
 * 测试开放地址法的散列表
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/08
 */
public class TestHashOpenAddressing {

  @Test
  public void testHash() {
    HashOpenAddressing hash = new HashOpenAddressing(16);

    for (int i = 0; i < 16; i++) {
      hash.put(String.valueOf(i), String.valueOf(i));
    }

    for (int i = 0; i < 16; i++) {
      System.out.println(hash.get(String.valueOf(i)));
    }
  }
}
