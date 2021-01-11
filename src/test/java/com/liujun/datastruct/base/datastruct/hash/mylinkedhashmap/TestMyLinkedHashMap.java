package com.liujun.datastruct.base.datastruct.hash.mylinkedhashmap;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * 测试linkedHashMap
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMyLinkedHashMap {

  @Test
  public void linkedTest() {
    MyLinkedHashMap<Integer, Integer> instance = new MyLinkedHashMap<>();
    instance.put(1, 1);
    instance.put(2, 2);
    instance.put(3, 3);
    instance.put(4, 4);
    instance.put(5, 5);

    Iterator<Integer> dataIter = instance.iterator();

    Assert.assertEquals((Integer) 1, dataIter.next());
    Assert.assertEquals((Integer) 2, dataIter.next());
    Assert.assertEquals((Integer) 3, dataIter.next());
    Assert.assertEquals((Integer) 4, dataIter.next());
    Assert.assertEquals((Integer) 5, dataIter.next());
  }

  @Test
  public void linkedTestGet() {
    MyLinkedHashMap<Integer, Integer> instance = new MyLinkedHashMap<>();
    instance.put(1, 1);
    instance.put(2, 2);
    instance.put(3, 3);
    instance.put(4, 4);
    instance.put(5, 5);

    int value = instance.get(2);
    Assert.assertEquals(value, 2);
    int value2 = instance.get(1);
    Assert.assertEquals(value2, 1);

    Iterator<Integer> dataIter = instance.iterator();

    Assert.assertEquals((Integer) 3, dataIter.next());
    Assert.assertEquals((Integer) 4, dataIter.next());
    Assert.assertEquals((Integer) 5, dataIter.next());
    Assert.assertEquals((Integer) 2, dataIter.next());
    Assert.assertEquals((Integer) 1, dataIter.next());
  }

  @Test
  public void linkedPut() {
    MyLinkedHashMap<Integer, Integer> instance = new MyLinkedHashMap<>(16, 5);
    instance.put(1, 1);
    instance.put(2, 2);
    instance.put(3, 3);
    instance.put(4, 4);
    instance.put(5, 5);

    int value = instance.get(2);
    Assert.assertEquals(value, 2);
    int value2 = instance.get(1);
    Assert.assertEquals(value2, 1);

    Iterator<Integer> dataIter = instance.iterator();
    Assert.assertEquals((Integer) 3, dataIter.next());
    Assert.assertEquals((Integer) 4, dataIter.next());
    Assert.assertEquals((Integer) 5, dataIter.next());
    Assert.assertEquals((Integer) 2, dataIter.next());
    Assert.assertEquals((Integer) 1, dataIter.next());

    instance.put(6, 6);
    instance.put(7, 7);

    dataIter = instance.iterator();
    Assert.assertEquals((Integer) 5, dataIter.next());
    Assert.assertEquals((Integer) 2, dataIter.next());
    Assert.assertEquals((Integer) 1, dataIter.next());
    Assert.assertEquals((Integer) 6, dataIter.next());
    Assert.assertEquals((Integer) 7, dataIter.next());
  }

  /** 测试冲突 */
  @Test
  public void testConflict() {
    int size = 10;

    MyLinkedHashMap instance = new MyLinkedHashMap(16, size);

    for (int k = 0; k < 10; k++) {
      for (int i = 0; i < size; i++) {
        instance.put(i, String.valueOf("name:" + i));
      }

      Iterator<Integer> dataIter = instance.iterator();
      for (int i = 0; i < size; i++) {
        Assert.assertEquals((Integer) i, dataIter.next());
      }
    }
  }
}
