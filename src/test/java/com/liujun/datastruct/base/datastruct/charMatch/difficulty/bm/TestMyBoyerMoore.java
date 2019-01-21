package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm;

import org.junit.Test;

/**
 * 用作测试bm算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/09
 */
public class TestMyBoyerMoore {

  @Test
  public void testBm() {
    String src = "this junjun";
    String find = "junjun";
    int index = MyBoyerMoore.INSTANCE.bm(src, find);
    System.out.println(index);
    if (index != -1) {
      System.out.println("截取后:" + src.substring(index, index + find.length()));
    }
  }
}
