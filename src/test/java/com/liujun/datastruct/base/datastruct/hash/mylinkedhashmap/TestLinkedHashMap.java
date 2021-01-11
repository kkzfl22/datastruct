package com.liujun.datastruct.base.datastruct.hash.mylinkedhashmap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 测试java的linkedHashMap
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestLinkedHashMap {

  @Test
  public void dataNormal() {
    LinkedHashMap<Integer, Integer> dataHash = new LinkedHashMap<>(5);
    dataHash.put(1, 1);
    dataHash.put(2, 2);
    dataHash.put(3, 3);
    dataHash.put(4, 4);
    dataHash.put(5, 5);
    dataHash.put(6, 6);

    Iterator<Integer> dataKeyIter = dataHash.keySet().iterator();
    Assert.assertEquals(dataKeyIter.next(), (Integer) 1);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 2);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 3);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 4);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 5);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 6);
  }
}
