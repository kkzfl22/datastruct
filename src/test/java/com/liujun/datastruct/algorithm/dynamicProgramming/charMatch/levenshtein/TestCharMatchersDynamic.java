package com.liujun.datastruct.algorithm.dynamicProgramming.charMatch.levenshtein;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/07
 */
public class TestCharMatchersDynamic {

  @Test
  public void testmatcher() {
    String src = "mitcmu";
    String target = "mtacnu";
    CharMatchersDynamic instance = new CharMatchersDynamic();
    int edst = instance.lwstbt(src.toCharArray(), target.toCharArray());
    System.out.println("莱文斯坦距离是:" + edst);
  }
}
