package com.liujun.datastruct.base.datastruct.charMatch.simple.bf;

import org.junit.Test;

/**
 * 测试BF算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class TestBructForce {

  @Test
  public void testBructForce() {
    BructForce instance = new BructForce();

    String src = "my name is liujun";
    String find = "liujun";

    int findIndex = instance.find(src, find);
    System.out.println("查找的位置索引为:" + findIndex);

    String sub = src.substring(findIndex);
    System.out.println("截取后的字符串为:" + sub);
  }

  @Test
  public void testBructForceNot() {
    BructForce instance = new BructForce();

    String src = "my name is liujun";
    String find = "liujun2";

    int findIndex = instance.find(src, find);
    System.out.println("查找的位置索引为:" + findIndex);
  }
}
