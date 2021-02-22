package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code705;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试哈希集合
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMyHashSet {

  @Test
  public void testHashSet() {
    MyHashSet myHashSet = new MyHashSet();
    // set = [1]
    myHashSet.add(1);
    // set = [1, 2]
    myHashSet.add(2);
    // 返回 True
    Assert.assertEquals(true, myHashSet.contains(1));
    // 返回 False ，（未找到）
    Assert.assertEquals(false, myHashSet.contains(3));
    // set = [1, 2]
    myHashSet.add(2);
    // 返回 True
    Assert.assertEquals(true, myHashSet.contains(2));
    // set = [1]
    myHashSet.remove(2);
    // 返回 False ，（已移除）
    Assert.assertEquals(false, myHashSet.contains(2));

    // set = [1]
    myHashSet.add(109);

    // 返回 true
    Assert.assertEquals(true, myHashSet.contains(109));
  }
}
