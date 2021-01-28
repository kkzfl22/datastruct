package com.liujun.datastruct.advanced.bloomfilter;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/15
 */
public class TestBitMap {

  @Test
  public void testmap() {
    BitMap instance = new BitMap(20);

    for (int i = 0; i < 10; i++) {
      instance.set(i);
    }
  }
}
