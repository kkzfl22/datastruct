package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm.use;

/**
 * 进行字符串bm算法的实现，此使用坏字符规则
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/17
 */
public class CharMatcherBMGoodSuffix extends CharMatcherBMBadChars {

  /** 用来记录跟后缀子串匹配的另一个前缀子串的索引位置 */
  private final int[] suffix;

  /** 在后缀子串中能与当前索引所对应的后缀子串匹配的前缀子串，true表示可以匹配，false 不能匹配 */
  private final boolean[] prefix;

  public CharMatcherBMGoodSuffix(String patterChar) {
    super(patterChar);
    this.suffix = new int[this.getMatcherLength()];
    this.prefix = new boolean[this.getMatcherLength()];
    this.generateGs(super.getMatcherChars());
  }

  /**
   * 进行填充suffix数组与prefix数组，用来进行好后缀规则的填充操作
   *
   * @param matchers 模式串
   */
  public void generateGs(byte[] matchers) {
    int matcherLength = matchers.length;

    // 进行初始化
    for (int i = 0; i < matcherLength; i++) {
      suffix[i] = -1;
      prefix[i] = false;
    }

    // 从向前向后遍历前缀子串
    for (int i = 0; i < matcherLength - 1; i++) {
      int suffixIndex = i;

      // 公共后缀子串长度s
      int commSuffixLenght = 0;

      // 检查前缀子串与后缀子串是否匹配，如果匹配继续检查，不匹配，则结束
      while (suffixIndex >= 0
          && matchers[suffixIndex] == matchers[matcherLength - 1 - commSuffixLenght]) {
        suffixIndex--;
        commSuffixLenght++;
      }
      // suffixIndex + 1表示公共子串的起始下标
      if (commSuffixLenght != 0) {
        this.suffix[commSuffixLenght] = suffixIndex + 1;
      }
      // 如果前后相重合说明当前存在公共子串，则标训为公共子串
      if (suffixIndex == -1) {
        this.prefix[commSuffixLenght] = true;
      }
    }
  }

  @Override
  public int countMoveGoodSuffix(int badIndex) {
    int goodSuffixLength = super.getMatcherLength() - 1 - badIndex;
    // 如果当前字符在械串中存在，则计算跳过的位数
    if (this.suffix[goodSuffixLength] != -1) {
      return badIndex - suffix[goodSuffixLength] + 1;
    }

    // 好后缀的后缀子串是否存在跟模式串的前缀子串匹配的
    for (int i = badIndex + 2; i <= super.getMatcherLength() - 1; i++) {
      if (this.prefix[super.getMatcherLength() - i]) {
        return i;
      }
    }

    // 如果未发生匹配，则直接跳过指定的位数
    return super.getMatcherLength();
  }
}
