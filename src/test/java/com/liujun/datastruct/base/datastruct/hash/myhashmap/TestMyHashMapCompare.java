package com.liujun.datastruct.base.datastruct.hash.myhashmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

/**
 * 进行自定义的MyHashMap与java原生的HashMap简单对比测试
 *
 * @author liujun
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMyHashMapCompare {

  private static final int MAX_SIZE = 1999999;

  @Test
  public void testHashMapMa() {

    HashMap instanceMap = new HashMap();
    for (int i = 0; i < MAX_SIZE; i++) {
      Data item = new Data(i, "i" + i);
      instanceMap.put(item, "name:" + i);
    }

    for (int i = MAX_SIZE - 1; i > 0; i--) {
      Data item = new Data(i, "i" + i);
      Object value = instanceMap.get(item);
      Assert.assertEquals(value, "name:" + i);
      instanceMap.remove(item);
    }
  }

  /** 自己实现的HashMap测试 */
  @Test
  public void testHashMapMe() {

    MyHashMap instance = new MyHashMap();

    for (int i = 0; i < MAX_SIZE; i++) {
      Data item = new Data(i, "i" + i);
      instance.put(item, "name:" + i);
    }

    for (int i = MAX_SIZE - 1; i > 0; i--) {
      Data item = new Data(i, "i" + i);
      Object value = instance.get(item);
      Assert.assertEquals(value, "name:" + i);
      instance.remove(item);
    }
  }
}
