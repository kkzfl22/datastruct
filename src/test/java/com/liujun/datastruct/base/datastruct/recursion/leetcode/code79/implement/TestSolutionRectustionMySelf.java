package com.liujun.datastruct.base.datastruct.recursion.leetcode.code79.implement;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/26
 */
public class TestSolutionRectustionMySelf {

  @Test
  public void testMstach() {
    char[][] src = new char[][] {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
    SolutionRectustionMySelf instance = new SolutionRectustionMySelf();

    boolean result = instance.exist(src, "ABCCED");
    Assert.assertEquals(true, result);

    result = instance.exist(src, "SEE");
    Assert.assertEquals(true, result);

    result = instance.exist(src, "ABCB");
    Assert.assertEquals(false, result);
  }

  @Test
  public void testMstach2() {
    char[][] src = new char[][] {{'a', 'b'}};
    SolutionRectustionMySelf instance = new SolutionRectustionMySelf();

    boolean result = instance.exist(src, "ba");
    Assert.assertEquals(true, result);
  }

  @Test
  public void testMstach3() {
    char[][] src =
        new char[][] {
          {'F', 'Y', 'C', 'E', 'N', 'R', 'D'},
          {'K', 'L', 'N', 'F', 'I', 'N', 'U'},
          {'A', 'A', 'A', 'R', 'A', 'H', 'R'},
          {'N', 'D', 'K', 'L', 'P', 'N', 'E'},
          {'A', 'L', 'A', 'N', 'S', 'A', 'P'},
          {'O', 'O', 'G', 'O', 'T', 'P', 'N'},
          {'H', 'P', 'O', 'L', 'A', 'N', 'O'}
        };
    SolutionRectustionMySelf instance = new SolutionRectustionMySelf();

    boolean result = instance.exist(src, "INDIA");
    Assert.assertEquals(false, result);
  }
}
