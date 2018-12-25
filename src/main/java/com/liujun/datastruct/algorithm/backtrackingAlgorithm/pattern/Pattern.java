package com.liujun.datastruct.algorithm.backtrackingAlgorithm.pattern;

/**
 * 使用回塑完成正则表达式的匹配
 *
 * <p>使用正则的问题，就存在的3种情况
 *
 * <p>1，字符完全匹配
 *
 * <p>2，使用*通配符匹配
 *
 * <p>3，使用？通配符匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class Pattern {

  private boolean matched = false;

  /** 正则表达式 */
  private char[] pattern;

  /** 正则表达式的长度 */
  private int plen;

  public Pattern(String patternStr, int plen) {
    char[] patters = patternStr.toCharArray();
    this.pattern = patters;
    this.plen = plen;
  }

  /**
   * 匹配的字符长度
   *
   * @param textStr 原始字符信息
   * @return 是否能匹配
   */
  public boolean match(String textStr) {
    matched = false;
    char[] texts = textStr.toCharArray();
    rmatch(0, 0, texts, texts.length);
    return matched;
  }

  /**
   * 进行匹配操作
   *
   * @param ti 当前匹配的字符的下标
   * @param pi 模式串的下标
   * @param text 匹配的文本
   * @param tlen 文本的长度
   */
  private void rmatch(int ti, int pi, char[] text, int tlen) {

    // 如果当前已经匹配，就不正继续递归
    if (matched) {
      return;
    }
    // 如果正则表达式到结尾了
    if (pi == plen) {
      // 检查文本匹配是否也已经到结果了
      if (ti == tlen) {
        matched = true;
      }
      return;
    }

    // 如果为*，则匹配任意个字符
    if (pattern[pi] == '*') {
      for (int i = 0; i <= tlen - ti; i++) {
        rmatch(ti + i, pi + 1, text, tlen);
      }
    }
    // 如果为?，则匹配0个或者1个字符
    else if (pattern[pi] == '?') {
      rmatch(ti, pi + 1, text, tlen);
      rmatch(ti + 1, pi + 1, text, tlen);
    }
    // 其他情况则为纯字符的匹配操作
    else if (ti < tlen && pattern[pi] == text[ti]) {
      rmatch(ti + 1, pi + 1, text, tlen);
    }
  }
}
