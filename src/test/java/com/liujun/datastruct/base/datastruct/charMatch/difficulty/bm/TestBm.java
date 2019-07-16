package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class TestBm {

  @Test
  public void testBm() {
    BoyerMoore instace = new BoyerMoore();

    String src =  "eaaccccc";
    String find = "cccc";

    int index = instace.bm(src, find);
    System.out.println("当前位置为:" + index);

    if (index != -1) {
      System.out.println("截取:" + src.substring(index, index + find.length()));
    }
  }


  @Test
  public void testBm2() {
    BoyerMoore instace = new BoyerMoore();

    String src =
        "<a href=\"http://news.sohu.com/s2018/guoqing69/index.shtml\" target=\"_blank\"></a>";
    String find = "href=\"";

    int index = instace.bm(src, find);
    System.out.println("当前位置为:" + index);

    if (index != -1) {
      System.out.println("截取:" + src.substring(index, index + find.length()));
    }
  }

  @Test
  public void testGeneratsGS() {
    String find = "cccccc";
    int length = find.length();

    BoyerMoore instace = new BoyerMoore();
    int[] suffix = new int[length];
    boolean[] prefix = new boolean[length];

    instace.generatsGS(find.toCharArray(), suffix, prefix);

    System.out.println(Arrays.toString(suffix));
    System.out.println(Arrays.toString(prefix));
  }
}
