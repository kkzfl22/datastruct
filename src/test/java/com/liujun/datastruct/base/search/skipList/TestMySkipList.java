package com.liujun.datastruct.base.search.skipList;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * 测试跳表
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMySkipList {

  @Test
  public void testSkipList() {

    MySkipList<Integer> skipList = new MySkipList<>(4);

    for (int i = 0; i < 10; i++) {
      skipList.insert(8);
      skipList.insert(9);
      skipList.insert(10);
      skipList.insert(5);
      skipList.insert(4);
      skipList.insert(2);
      skipList.insert(1);
      Assert.assertNotNull(skipList.find(5));
      skipList.delete(5);
      Assert.assertNull(skipList.find(5));

      skipList.insert(6);
      skipList.insert(7);

      List<Integer> scopeList = skipList.scopeData(4, 10);
      Assert.assertThat(scopeList, Matchers.hasItems(4, 6, 7, 8, 9));
      Iterator<Integer> iteratorList = skipList.getIterator();

      while (iteratorList.hasNext()) {
        System.out.println(iteratorList.next());
      }
      System.out.println("======================");
    }
  }

  @Test
  public void testCompareData() {
    Integer item1 = 1;
    Integer item2 = 2;

    Comparable data1 = item1;
    System.out.println(data1.compareTo(item2));
  }
}
