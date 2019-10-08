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
public class RegularMatchingSolutionOtherTest {

  private RegularMatchingSolutionOther1 instance = new RegularMatchingSolutionOther1();

  @Test
  public void testMatch() {

    boolean matchRsp = instance.isMatch("aa", "a");
    Assert.assertEquals(matchRsp, false);

    Assert.assertEquals(instance.isMatch("abc", "abd"), true);
    Assert.assertEquals(instance.isMatch("abcd", "abcd"), true);

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

  @Test
  public void testMatch5() {
    Assert.assertEquals(instance.isMatch("aaabcx1234576", ".*"), true);
  }

  @Test
  public void testMatch6() {
    Assert.assertEquals(instance.isMatch("aaaaaaaaaa", "a*"), true);
    Assert.assertEquals(instance.isMatch("aaaaaaaaba", "a*"), false);
  }


  @Test
  public void testMatch7() {
    Assert.assertEquals(instance.isMatch("aaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*c"), false);
  }

}
