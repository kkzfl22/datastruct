package com.liujun.datastruct.base.datastruct.hash.openaddressing;

import org.junit.Test;

/**
 * 使用二次探测来解决散列冲突的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/08
 */
public class TestHashOpenAddressingQuadraticProbing {

  @Test
  public void hash() {
    int maxSize = 512;
    HashOpenAddressingQuadraticProbing hash = new HashOpenAddressingQuadraticProbing(maxSize);

    int countValue = Math.round(maxSize * 0.5f);

    System.out.println("大小:" + countValue);

    for (int i = 0; i < countValue; i++) {
      boolean putRsp = hash.put(String.valueOf(i), String.valueOf(i));
      if (!putRsp) {
        System.out.println("放入失败结果:" + i + "," + putRsp);
      }
    }

    for (int i = 0; i < countValue; i++) {
      String value = hash.get(String.valueOf(i));
      if (null == value) {
        System.out.println("获取结果失败:" + i + ",value:" + value);
      }
    }
  }
}
