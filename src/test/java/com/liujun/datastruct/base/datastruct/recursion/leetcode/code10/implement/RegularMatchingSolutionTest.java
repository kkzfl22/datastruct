package com.liujun.datastruct.base.datastruct.recursion.leetcode.code10.implement;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试正则表达式的回塑匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/01
 */
public class RegularMatchingSolutionTest {

  private RegularMatchingSolution instance = new RegularMatchingSolution();

  @Test
  public void testMatch() {

    boolean matchRsp = instance.isMatch("aa", "a");
    Assert.assertEquals(matchRsp, false);

    matchRsp = instance.isMatch("aa", "a*");
    Assert.assertEquals(matchRsp, true);

    matchRsp = instance.isMatch("ab", ".*");
    Assert.assertEquals(matchRsp, true);



    matchRsp = instance.isMatch("mississippi", "mis*is*p*.");
    Assert.assertEquals(matchRsp, false);
  }

  @Test
  public void testMatch2() {
    Assert.assertEquals(instance.isMatch("aab", "c*a*b"), true);
  }

  @Test
  public void testMatch3() {
    Assert.assertEquals(instance.isMatch("ab", ".*c"), false);
  }

    @Test
    public void testMatch4() {
    Assert.assertEquals(instance.isMatch("aaa", "aaaa"), false);
    }
}
