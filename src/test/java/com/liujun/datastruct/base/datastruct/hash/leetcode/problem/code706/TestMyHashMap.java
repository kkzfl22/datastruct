package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code706;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestMyHashMap {

  @Test
  public void testMap() {
    MyHashMap hashMap = new MyHashMap();
    hashMap.put(1, 1);
    hashMap.put(2, 2);
    // 返回 1
    hashMap.get(1);
    // 返回 -1 (未找到)
    hashMap.get(3);
    // 更新已有的值
    hashMap.put(2, 1);
    // 返回 1
    hashMap.get(2);
    // 删除键为2的数据
    hashMap.remove(2);
    // 返回 -1 (未找到)
    hashMap.get(2);

    hashMap.put(1025, 1);
    hashMap.remove(1025);
    hashMap.remove(1);
  }
}
