package com.liujun.datastruct.base.datastruct.hash.myhashmap;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * 进行自定义的MyHashMap测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMyHashMap {

  @Test
  public void testHashMap() {
    MyHashMap instance = new MyHashMap();
    instance.put("111", "123");
    Assert.assertEquals(instance.isEmpty(), false);
    Assert.assertEquals(instance.containsKey("111"), true);
    Assert.assertEquals(instance.size(), 1);

    instance.remove("111");
    Assert.assertEquals(instance.containsKey("111"), false);
    Assert.assertEquals(instance.isEmpty(), true);

    Assert.assertEquals(instance.size(), 0);
    instance.put("111", "123");
    instance.clean();
    Assert.assertEquals(instance.containsKey("111"), false);
    Assert.assertEquals(instance.isEmpty(), true);
  }

  /** 测试冲突 */
  @Test
  public void testConflict() {
    MyHashMap instance = new MyHashMap();

    for (int k = 0; k < 10; k++) {
      for (int i = 0; i < 512; i++) {
        Data item = new Data(i, "i" + i);
        instance.put(item, String.valueOf("name:" + i));
      }

      for (int i = 0; i < 512; i++) {
        Data item = new Data(i, "i" + i);
        Object value = instance.get(item);
        Assert.assertEquals(value, "name:" + i);
        instance.remove(item);
      }
      Assert.assertEquals(instance.size(), 0);
      Assert.assertEquals(instance.isEmpty(), true);
      Assert.assertEquals(instance.checkEmpty(), true);
    }
  }

  /** 大数据量的测试 */
  @Test
  public void testConflictBig() {
    int maxSize = 2999999;

    MyHashMap instance = new MyHashMap();

    for (int i = 0; i < maxSize; i++) {
      Data item = new Data(i, "i" + i);
      instance.put(item, String.valueOf("name:" + i));
    }

    for (int i = maxSize - 1; i > 0; i--) {
      Data item = new Data(i, "i" + i);
      Object value = instance.get(item);
      // System.out.println(value);
      Assert.assertEquals(value, "name:" + i);
      instance.remove(item);
    }
  }

  @Test
  public void testHashMapGet() {
    int maxSize = 2999999;

    HashMap instance = new HashMap();
    for (int i = 0; i < maxSize; i++) {
      Data item = new Data(i, "i" + i);
      instance.put(item, String.valueOf("name:" + i));
    }

    for (int i = maxSize - 1; i > 0; i--) {
      Data item = new Data(i, "i" + i);
      Object value = instance.get(item);
      // System.out.println(value);
      Assert.assertEquals(value, "name:" + i);
      instance.remove(item);
    }
  }

  /** 测试扩容 */
  @Test
  public void testMore() {
    MyHashMap instance = new MyHashMap(8);
    for (int i = 0; i < 512; i++) {
      instance.put(i, String.valueOf("name:" + i));
    }

    Object value = instance.get(2);
    Assert.assertEquals(value, "name:2");
    Object valueNine = instance.get(9);
    Assert.assertEquals(valueNine, "name:9");
    Object valueMore = instance.get(20);
    Assert.assertEquals(valueMore, "name:20");
  }

  @Test
  public void testHash() {
    Data item1 = new Data(0, "i" + 0);
    Data item2 = new Data(0, "i" + 0);
    System.out.println(item1.hashCode());
    System.out.println(item2.hashCode());
  }
}
