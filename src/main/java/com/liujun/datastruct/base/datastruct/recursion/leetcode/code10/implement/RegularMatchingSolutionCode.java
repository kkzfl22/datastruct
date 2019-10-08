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
public class RegularMatchingSolutionCode {

  public boolean isMatch(String text, String pattern) {
    // 如果都为空则匹配成功
    if (pattern.isEmpty()) return text.isEmpty();

    // 第一个是否匹配上
    boolean first_match =
        (!text.isEmpty() && (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

    if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
      // 看有没有可能,剩下的pattern匹配上全部的text
      // 看有没有可能,剩下的text匹配整个pattern
      // isMatch(text, pattern.substring(2))
      // 指当p第二个为*时，前面的字符不影响匹配所以可以忽略，所以将*以及*之前的一个字符删除后匹配之后的字符，这就是为什么用pattern.substring(2)
      // 如果第一个已经匹配成功，并且第二个字符为*时，这是我们就要判断之后的需要匹配的字符串是否是多个前面的元素（*的功能），这就是first_match &&
      // isMatch(text.substring(1), pattern))的意义
      return (isMatch(text, pattern.substring(2))
          || (first_match && isMatch(text.substring(1), pattern)));
    } else {
      // 没有星星的情况:第一个字符相等,而且剩下的text,匹配上剩下的pattern，没有星星且第一个匹配成功，那么s和p同时向右移动一位看是否仍然能匹配成功
      return first_match && isMatch(text.substring(1), pattern.substring(1));
    }
  }
}
