package com.liujun.datastruct.datastruct.charMatch.simple.rk;

import org.junit.Test;

/**
 * 测试rk算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class TestRabinKarp {

  @Test
  public void testRk() {
    RabinKarp rk = new RabinKarp();

    String src = "aa bb cc dd";
    String find = "dd";

    int findIndex = rk.find(src, find);
    System.out.println("查找的位置索引为:" + findIndex);

    if (findIndex != -1) {
      String sub = src.substring(findIndex);
      System.out.println("截取后的字符串为:" + sub);
    }
  }
}
