package com.liujun.datastruct.base.datastruct.recursion.leetcode.code10.implement;

/**
 * 进行正则表达式的匹配操作
 *
 * <p>首先优先进行字符串的匹配，如果字符串可以匹配，则继续，不能匹配则继续
 *
 * <p>则进行'.' 匹配任意单个字符
 *
 * <p>'*' 匹配零个或多个前面的那一个元素
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/29
 */
public class RegularMatchingSolution {

  /** 模式单个字符 */
  private static final char PATTERN_MATCH = '.';

  /** 进行多个字符的匹配 */
  private static final char PATTERN_MORE_MATCH = '*';

  /**
   * @param s 字符串s
   * @param p 字符的规律P
   * @return true 表示可以匹配,false表示不能匹配
   */
  public boolean isMatch(String s, String p) {

    // 如果输入为空，返回匹配失败
    if (s == null || p == null) {
      return false;
    }

    char[] src = s.toCharArray();
    char[] patterns = p.toCharArray();

    return this.match(src, patterns, 0, 0);
  }

  private boolean match(char[] src, char[] pattern, int charindex, int patternindex) {

    // 1,如果模式串与字符串都能完全进行匹配，则返回为true
    if (pattern.length == patternindex && charindex == src.length) {
      return true;
    }

    // 如果仅模式串匹配，但字符串不匹配，则返回false
    else if (pattern.length == patternindex && charindex != src.length) {
      return false;
    }

    // 分为两种情况，一为*，多模式串的匹配
    if (patternindex < pattern.length - 1 && pattern[patternindex + 1] == PATTERN_MORE_MATCH) {
      // 针对此情况，又可以分为两种情况
      // 如果当前为字符或者.可进行任意的匹配操作
      if (charindex < src.length
          && (pattern[patternindex] == src[charindex] || pattern[patternindex] == PATTERN_MATCH)) {
        // 当间为字符与模式串都向后移动
        return match(src, pattern, patternindex + 1, patternindex + 2)
            // 当前仅字符移动，模式串不动
            || match(src, pattern, charindex + 1, patternindex)
            // 当前仅模式串移动，字符串不动
            || match(src, pattern, charindex, patternindex + 2);
      }
      // 其他情况，仅模式串向后移动
      else {
        return match(src, pattern, charindex, patternindex + 2);
      }
    }
    // 或者.与具体字符的单模式串匹配,如果可以匹配，直接向后加1
    if (charindex < src.length
        && (pattern[patternindex] == src[charindex] || pattern[patternindex] == PATTERN_MATCH)) {
      return match(src, pattern, charindex + 1, patternindex + 1);
    }

    return false;
  }
}
