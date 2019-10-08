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
public class RegularMatchingSolutionOther1 {

  public boolean isMatch(String src, String pattenr) {
    char[] srcchars = src.toCharArray();
    char[] patterns = pattenr.toCharArray();

    return this.match(srcchars, patterns);
  }

  public boolean match(char[] str, char[] pattern) {
    // 如果输入为空，返回匹配失败
    if (str == null || pattern == null) return false;

    return match(str, pattern, 0, 0);
  }

  private boolean match(char[] str, char[] pattern, int strIndex, int patIndex) {
    // 如果字符串和模式同时遍历完成，说明匹配成功
    if (strIndex == str.length && patIndex == pattern.length) return true;
    // 如果字符串还没有遍历完，模式已经遍历完，说明匹配失败
    if (strIndex != str.length && patIndex == pattern.length) return false;

    // 【1】当模式中的第二个字符是“*”时：
    if (patIndex < pattern.length - 1 && pattern[patIndex + 1] == '*') {
      // 2、如果字符串第一个字符跟模式第一个字符匹配(相同或者模式为'.')，可以有3种匹配方式：
      if (strIndex < str.length
          && (pattern[patIndex] == str[strIndex] || pattern[patIndex] == '.')) {
        // （2）字符串后移1字符，模式后移2字符；
        return match(str, pattern, strIndex + 1, patIndex + 2)
            // （3）字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位；
            || match(str, pattern, strIndex + 1, patIndex)
            // （1）模式后移2字符，相当于x*被忽略；
            || match(str, pattern, strIndex, patIndex + 2);
      } else {
        // 1、如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。
        return match(str, pattern, strIndex, patIndex + 2);
      }
    }
    // 【2】当模式中的第二个字符不是“*”时：tips!!!切记要把对下标的合法性检查放在&&左侧
    if (strIndex < str.length && (pattern[patIndex] == str[strIndex] || pattern[patIndex] == '.')) {
      // 1、如果字符串第一个字符和模式中的第一个字符相匹配
      return match(str, pattern, strIndex + 1, patIndex + 1);
    }
    // 2、如果字符串第一个字符和模式中的第一个字符相不匹配，直接返回false
    return false;
  }
}
