package com.liujun.datastruct.charMatch.simple.rk;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class TestRabinKarp2 {

  @Test
  public void testFind() {
    String src = "my name is liujun THIS NAME IS ZFL";
    String find = "NAME";

    RabinKarp2 findInstance = new RabinKarp2();
    int findIndex = findInstance.find(src, find);
    System.out.println("查找的索引号为 ：" + findIndex);

    if (-1 != findIndex) {
      System.out.println("截取字符串为:" + src.substring(findIndex, findIndex + find.length()));
    }
  }
}
