package com.liujun.datastruct.base.search.skipList.leetcode1206;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试跳表
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSkiplist2 {

  @Test
  public void testSkipList() {
    Skiplist2 skiplist = new Skiplist2();

    skiplist.add(1);
    skiplist.add(2);
    skiplist.add(3);
    boolean rsp = skiplist.search(0); // 返回 false
    Assert.assertEquals(false, rsp);
    skiplist.add(4);
    boolean searchExists = skiplist.search(1); // 返回 true
    Assert.assertEquals(true, searchExists);

    boolean notExists = skiplist.erase(0); // 返回 false，0 不在跳表中
    Assert.assertEquals(false, notExists);

    boolean eraseRsp = skiplist.erase(1); // 返回 true
    Assert.assertEquals(true, eraseRsp);

    boolean deleteRsp = skiplist.search(1); // 返回 false，1 已被擦除
    Assert.assertEquals(false, deleteRsp);
  }
}
