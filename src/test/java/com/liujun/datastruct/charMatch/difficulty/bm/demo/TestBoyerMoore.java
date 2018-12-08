package com.liujun.datastruct.charMatch.difficulty.bm.demo;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class TestBoyerMoore {

  @Test
  public void testGenerateGs() {

    BoyerMoore instance = new BoyerMoore();

    String find = "feife";
    int length = find.length();

    int[] suffix = new int[length];
    boolean[] prefix = new boolean[length];

    instance.generateGS(find.toCharArray(), length, suffix, prefix);

    System.out.println(Arrays.toString(suffix));
    System.out.println(Arrays.toString(prefix));
  }
}
