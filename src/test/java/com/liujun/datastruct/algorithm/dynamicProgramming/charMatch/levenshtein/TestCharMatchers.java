package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.levenshtein;

import org.junit.Test;

/**
 * 字符进行匹配的情况
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/06
 */
public class TestCharMatchers {

  @Test
  public void testmatcher() {
    String src = "mitcmu";
    String target = "mtacnu";
    CharMatchers instance = new CharMatchers();
    instance.lwstbt(0, 0, 0, src.toCharArray(), target.toCharArray());
    System.out.println("莱文斯坦距离是:" + instance.minDest);
  }
}
