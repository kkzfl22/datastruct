package com.liujun.datastruct.algorithm.backtrackingAlgorithm.pattern;

/**
 * 使用回溯的思想来完成正则表达式的示例
 *
 * <p>分为三种
 *
 * <p>1，字符匹配
 *
 * <p>2，*匹配
 *
 * <p>3，？匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class MyPattern {

  /** 是否匹配的标识 */
  private boolean match = false;

  /** 正则表达式串的信息 */
  private char[] patterns;

  /** 正则表达式的长度 */
  private int plen;

  public MyPattern(String patternStr) {
    char[] patterns = patternStr.toCharArray();
    this.patterns = patterns;
    this.plen = patterns.length;
  }

  /**
   * 进行字符的匹配匹配是
   *
   * @param matchStr 匹配的字符串信息
   * @return true 表示匹配，false 表示不匹配
   */
  public boolean match(String matchStr) {
    match = false;
    char[] mcArryas = matchStr.toCharArray();
    this.recyMatch(0, 0, mcArryas, mcArryas.length);
    return match;
  }

  /**
   * 进行递归的匹配操作
   *
   * @param matIndex 字符的当前匹配的索引
   * @param patIndex 正则匹配的下标索引
   * @param text 文本串
   * @param tlen 文本串的长度
   */
  private void recyMatch(int matIndex, int patIndex, char[] text, int tlen) {
    // 如果已经匹配，则返回
    if (match) {
      return;
    }
    // 检查正则长度是否匹配
    if (patIndex == plen) {
      // 检查字符串的长度是否匹配
      if (matIndex == tlen) {
        match = true;
      }
      return;
    }

    // 如果当前为*表示是所有字符匹配
    if (patterns[patIndex] == '*') {
      for (int i = 0; i <= tlen - matIndex; i++) {
        recyMatch(matIndex + i, patIndex + 1, text, tlen);
      }
    }
    // 如果当前匹配为 ？ ，匹配0个或者1个的情况
    else if (patterns[patIndex] == '?') {
      recyMatch(matIndex, patIndex + 1, text, tlen);
      recyMatch(matIndex + 1, patIndex + 1, text, tlen);
    }
    // 进行字符的匹配操作
    else if (matIndex < tlen && text[matIndex] == patterns[patIndex]) {
      recyMatch(matIndex + 1, patIndex + 1, text, tlen);
    }
  }
}
