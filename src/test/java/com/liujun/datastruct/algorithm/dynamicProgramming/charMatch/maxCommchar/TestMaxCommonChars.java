package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.maxCommchar;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/08
 */
public class TestMaxCommonChars {

  /** 求最大公共子串的长度 */
  @Test
  public void testCountMaxCommonChars() {

    String src = "mitcmu";
    String target = "mtacnu";

    MaxCommonChars instance = new MaxCommonChars();
    instance.recursionCount(src.toCharArray(), 0, target.toCharArray(), 0, 0);
    System.out.println("最大公共子串:" + instance.maxCharNumScope);
  }

  /** 求最大公共子串的长度 */
  @Test
  public void testCountMaxCommonCharsDynamic() {

    //    String src = "mitcmu";
    //    String target = "mtacnu";

    String src = "mitcmu";
    String target = "mitcmu";

    MaxCommonCharsDynamic instance = new MaxCommonCharsDynamic();
    int maxCount = instance.recursionCount(src.toCharArray(), target.toCharArray());
    System.out.println("最大公共子串:" + maxCount);
  }
}
