package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm.use;

import org.junit.Assert;
import org.junit.Test;

/**
 * 在坏字符规则的基础上，加入好后缀规则，进行字符串的匹配操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class TestCharMatcherBMGoodSuffix {

  /** 字符串的查找操作，加入好后缀规则验证坏字符的规则 */
  @Test
  public void test01GoogSuffixCharMatcher() {
    String matchers = "abd";
    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getGoodSuffixInstance(matchers);

    String src = "abcacabdc";

    int startIndex = 0;

    int findIndex = instance.matcherIndex(src, startIndex);
    Assert.assertEquals(5, findIndex);
  }

  /** 字符串的查找操作,加入好后缀规则 */
  @Test
  public void test02GoodSuffixCharMatcher() {

    String src = "abcacabcbacabc";
    String matchers = "cbacabc";
    int index = 0;

    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getGoodSuffixInstance(matchers);

    int findIndex = instance.matcherIndex(src, index);
    Assert.assertEquals(7, findIndex);

    String src2 = "3333333333333";
    int findIndex2 = instance.matcherIndex(src2, index);
    Assert.assertEquals(-1, findIndex2);
  }

  /** 字符串的查找操作,加入好后缀规则 */
  @Test
  public void test03GoodSuffixCharMatcher() {

    String src =
        "<a href=\"http://news.sohu.com/s2018/guoqing69/index.shtml\" target=\"_blank\"></a>";
    String matchers = "href=\"";
    int index = 0;

    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getGoodSuffixInstance(matchers);

    int findIndex = instance.matcherIndex(src, index);
    Assert.assertEquals(3, findIndex);
  }

  /** 字符串的查找操作,加入好后缀规则 */
  @Test
  public void test05GoodSuffixChina() {

    String src = "中国人民解放军1231adb";
    String matchers = "adb";
    int index = 0;

    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getGoodSuffixInstance(matchers);

    int findIndex = instance.matcherIndex(src, index);
    Assert.assertEquals(11, findIndex);

    String src2 = "3333333333333";
    int findIndex2 = instance.matcherIndex(src2, index);
    Assert.assertEquals(-1, findIndex2);
  }

  /** 字符串的查找操作,加入好后缀规则 */
  @Test
  public void test06GoodSuffixChina() {

    String src = "中国人民解放军12adbsc\r\n\r\n人民";
    String matchers = "\r\n\r\n";
    int index = 0;

    CharMatcherBMBadChars instance = CharMatcherBMGoodSuffix.getGoodSuffixInstance(matchers);

    int findIndex = instance.matcherIndex(src, index);
    Assert.assertEquals(14, findIndex);
  }
}
