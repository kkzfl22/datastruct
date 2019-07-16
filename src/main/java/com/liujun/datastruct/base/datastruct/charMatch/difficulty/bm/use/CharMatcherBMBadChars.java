package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm.use;

/**
 * 高效的字符串匹配算法bm算法,使用坏字符的规则
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/03/15
 */
public class CharMatcherBMBadChars {

  /** 字符集大小 */
  public static final int BUFFER_SIZE = 256;

  /** 模式串的坏字符规则 ，预算计算 */
  private final int[] badChar;

  /** 模式串的长度 */
  private final int matcherLength;

  /** 模式串信息 */
  private final byte[] matcherChars;

  public CharMatcherBMBadChars(String patterChar) {
    assert patterChar != null : "patterChar is not char";

    // 模式串信息
    this.matcherChars = GetBytes(patterChar);
    // 设置模式串的长度
    this.matcherLength = matcherChars.length;
    // 进行坏字符规则的设置操作
    this.badChar = this.generateBc(matcherChars);
  }

  /**
   * 将字符转换为byte的特殊方法
   *
   * <p>按char字符的长度，来转换为byte的长度，中文切分将出现问题
   *
   * @param patterChar 字符串，
   * @return byte信息
   */
  public static byte[] GetBytes(String patterChar) {
    char[] cs = patterChar.toCharArray();
    // 定义一个长度与需要转换的char数组相同的byte数组
    byte[] bs2 = new byte[cs.length];
    // 循环将char数组的每一个元素转换为byte并存在上面定义的byte数组中
    for (int i = 0; i < cs.length; i++) {
      // 将每一个char转换成byte
      byte b = (byte) cs[i];
      // 保存到数组中
      bs2[i] = b;
    }

    return bs2;
  }

  /**
   * 获取字符串匹配的实例对象,此处生成生成坏字符规则的实例对象
   *
   * @param patterChar 模式串
   * @return 实例对象信息
   */
  public static CharMatcherBMBadChars getBadInstance(String patterChar) {
    return new CharMatcherBMBadChars(patterChar);
  }

  /**
   * 用来实现好后缀规则的代码实现
   *
   * <p>止方法比较节省内存，但速率比带好后缀规则的速度慢一点
   *
   * @param patterChar 模式串
   * @return 返回好后缀规则的实例对象
   */
  public static CharMatcherBMBadChars getGoodSuffixInstance(String patterChar) {
    return new CharMatcherBMGoodSuffix(patterChar);
  }

  /**
   * 将模式串进行预处理，使用hash来存储，计算每个字符最后出现的位置
   *
   * <p>速度最快，但需要额外的占用内存，对于速度要求高，采用此
   *
   * @param matchers
   * @return
   */
  private int[] generateBc(byte[] matchers) {
    int[] bc = new int[BUFFER_SIZE];

    for (int i = 0; i < BUFFER_SIZE; i++) {
      bc[i] = -1;
    }

    for (int i = 0; i < matchers.length; i++) {
      // 计算模式串主符的asc码值，然后以asc码值为索引计算下标
      int ascIndex = matchers[i];
      if (ascIndex >= 0 && ascIndex < BUFFER_SIZE) {
        bc[ascIndex] = i;
      }
    }

    return bc;
  }

  /**
   * 使用bm算法进行字符串的匹配操作
   *
   * @param str 原始字符串
   * @param inputStart 起始索引位置
   * @return 查找到的位置
   */
  public int matcherIndex(String str, int inputStart) {
    return this.matcherIndex(GetBytes(str), inputStart);
  }

  /**
   * 使用bm算法进行字符串的匹配操作
   *
   * @param str 原始字符信息
   * @param inputStart 起始索引位置
   * @return 查找到的位置
   */
  public int matcherIndex(byte[] str, int inputStart) {

    // 起始搜索位置
    int startIndex = inputStart;

    int srcLength = str.length;

    while (startIndex <= srcLength - matcherLength) {
      // 模式串的匹配规则，从后向前匹配
      int matchIndex;
      for (matchIndex = matcherLength - 1; matchIndex >= 0; matchIndex--) {
        // 检查当前是否不能匹配,如果不能匹配，坏字符就是当前matchIndex所对应的位置
        if (str[startIndex + matchIndex] != matcherChars[matchIndex]) {
          break;
        }
      }

      // 如果当前一直到模式串匹配完成，都能匹配，说明字符已经找到，则返回
      if (matchIndex < 0) {
        return startIndex;
      }

      int jumpBits = 0;

      int badCharCode = str[startIndex + matchIndex];

      // 检查是否超过了字符集的大小
      if (badCharCode > BUFFER_SIZE || badCharCode < 0) {
        jumpBits = matcherLength - 1;
      } else {
        // 如果出现坏字符，找到坏字符出现在模式串中的位置
        int badIndex = badChar[badCharCode];
        // 计算械串可跳过的位数
        jumpBits = matchIndex - badIndex;
      }

      int moveGoodSuffix = 0;

      // 使用好后缀的规则来进行计算滑动的位数
      // 在仅使用坏字符的规则下，此返回1
      if (matchIndex < matcherLength - 1) {
        moveGoodSuffix = this.countMoveGoodSuffix(matchIndex);
      }

      startIndex = startIndex + Math.max(jumpBits, moveGoodSuffix);
    }

    return -1;
  }

  /**
   * 使用好后后缀规则来进行计算的方法
   *
   * @param badIndex 坏字符索引位置
   * @return 使用好后缀规则移此的位数
   */
  public int countMoveGoodSuffix(int badIndex) {
    return 1;
  }

  public int[] getBadChar() {
    return badChar;
  }

  public int getMatcherLength() {
    return matcherLength;
  }

  public byte[] getMatcherChars() {
    return matcherChars;
  }
}
