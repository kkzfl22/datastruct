package com.liujun.utils;

import org.junit.Assert;

import java.util.List;

/**
 * 公共的一些较验
 *
 * @author liujun
 * @version 0.0.1
 */
public class AssertCheckUtils {

  public static final AssertCheckUtils INSTANCE = new AssertCheckUtils();

  /**
   * 断言两个集合是否一致
   *
   * @param src 源集合
   * @param target 目标集合
   */
  public void assertList(List<Integer> src, List<Integer> target) {
    Assert.assertEquals(src.size(), target.size());

    for (Integer item : src) {
      Assert.assertTrue(target.contains(item));
    }
  }
}
