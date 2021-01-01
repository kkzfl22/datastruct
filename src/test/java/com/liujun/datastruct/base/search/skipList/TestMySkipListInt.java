package com.liujun.datastruct.base.search.skipList;

import com.liujun.datastruct.base.search.skipList.MySkipListInt;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试跳表
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMySkipListInt {
  @Test
  public void skipTest() {
    MySkipListInt mySkipList = new MySkipListInt(4);
    mySkipList.add(8);
    mySkipList.add(4);
    mySkipList.add(2);
    mySkipList.add(1);
    mySkipList.add(9);
    mySkipList.add(15);

    Assert.assertEquals(true, mySkipList.find(9));
    mySkipList.delete(9);
    Assert.assertEquals(false, mySkipList.find(9));
  }
}
