package com.liujun.datastruct.base.datastruct.hash.myskiplistHashMap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 测试MySkipListHashMap这种数据结构
 *
 * <p>这种结构可以做到任何时间数据都可以按有序遍历
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMySkipListHashMap {

  @Test
  public void dataNormal() {
    MySkipListHashMap<Integer, Integer> dataHash = new MySkipListHashMap<>();
    dataHash.put(1, 1);
    dataHash.put(2, 2);
    dataHash.put(3, 3);
    dataHash.put(4, 4);
    dataHash.put(5, 5);
    dataHash.put(6, 6);
    dataHash.remove(6);
    dataHash.remove(5);
    dataHash.put(5, 5);
    dataHash.put(6, 6);

    Iterator<Integer> dataKeyIter = dataHash.valueIterator();
    Assert.assertEquals(dataKeyIter.next(), (Integer) 1);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 2);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 3);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 4);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 5);
    Assert.assertEquals(dataKeyIter.next(), (Integer) 6);
  }

  /** 测试扩容 */
  @Test
  public void dataMore() {
    MySkipListHashMap<Integer, Integer> dataHash = new MySkipListHashMap<>();
    for (int i = 0; i < 129; i++) {
      dataHash.put(i, i);
    }

    int value = 0;

    Iterator iter = dataHash.valueIterator();
    while (iter.hasNext()) {
      Assert.assertEquals(iter.next(), value);
      value++;
    }
  }

  /** 测试扩容 */
  @Test
  public void dataMoreGet() {
    MySkipListHashMap<Integer, Integer> dataHash = new MySkipListHashMap<>();
    int max = 720;

    for (int i = 0; i < max; i++) {
      dataHash.put(i, i);
    }

    for (int i = 0; i < max; i++) {
      Integer value = dataHash.get(i);
      Assert.assertEquals((Integer) i, value);
    }
  }

  /** 测试区间搜索 */
  @Test
  public void dataMoreScopeGet() {
    MySkipListHashMap<Integer, Integer> dataHash = new MySkipListHashMap<>();
    int max = 129;

    for (int i = 0; i < max; i++) {
      dataHash.put(i, i);
    }

    List<Integer> dataList = dataHash.scopeGet(10, 50);

    System.out.println(dataList);
  }

  /** 测试移除 */
  @Test
  public void dataRemove() {
    MySkipListHashMap<Integer, Integer> dataHash = new MySkipListHashMap<>();
    for (int i = 11; i >= 0; i--) {
      dataHash.put(i, i);
    }

    dataHash.remove(0);
    dataHash.remove(1);
    dataHash.remove(11);

    int value = 2;

    Iterator<Integer> iter = dataHash.valueIterator();
    while (iter.hasNext()) {
      Integer valueGet = iter.next();
      Assert.assertEquals(valueGet, (Integer) value);
      value++;
    }
  }

  /** 测试移除 */
  @Test
  public void dataRemoveReduce() {
    MySkipListHashMap<Integer, Integer> dataHash = new MySkipListHashMap<>();
    for (int i = 128; i >= 0; i--) {
      dataHash.put(i, i);
    }

    for (int i = 128; i >= 0; i--) {
      dataHash.remove(i);
      Assert.assertEquals(null, dataHash.get(i));

      int value = 0;
      Iterator<Integer> iter = dataHash.valueIterator();
      while (iter.hasNext()) {
        Integer valueGet = iter.next();
        Assert.assertEquals(valueGet, (Integer) value);
        value++;
      }
    }
  }

  @Test
  public void testTableSizeFor() {
    for (int i = 0; i < 129; i++) {
      System.out.println(i + " --> " + MySkipListHashMap.tableSizeFor(i / 2));
    }
  }
}
