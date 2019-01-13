package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm;

/**
 * BM算法，用来进行高效的字符串查找,
 *
 * <p>目前为止此算法的性能最高
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class MyBoyerMoore {

  /** 实例对象 */
  public static final MyBoyerMoore INSTANCE = new MyBoyerMoore();

  private static final int MAX_BYTE_SIZE = 256;

  /**
   * 使用bm算法进行高效字符查找
   *
   * @param src 原始字符串
   * @param find 待查找的字符串
   * @return 返回字符串的索引位置
   */
  public int bm(String src, String find) {

    char[] srcArrays = src.toCharArray();
    char[] findArrays = find.toCharArray();

    int srcLength = src.length();
    int findLength = find.length();

    // 1,构建坏字符集，使用邓处理，以加速性能
    int[] generBadCode = this.generateBadCode(findArrays);

    int[] suffix = new int[findLength];
    boolean[] prefix = new boolean[findLength];

    // 构建好后缀规则
    this.generGoodSuffix(findArrays, suffix, prefix);

    int index = 0;
    // 开始进行主串的遍历操作
    while (index <= srcLength - findLength) {
      // 进行与模式串的匹配操作，匹配的顺序从后向前进行匹配
      int j = 0;
      for (j = findLength - 1; j >= 0; j--) {
        // 找到坏字符时，则退出循环
        if (srcArrays[index + j] != findArrays[j]) {
          break;
        }
      }
      // 如果当前已经完全匹配，则退出查找
      if (j < 0) {
        return index;
      }

      // 计算跳过的位数
      // 获取坏字符在模式串中的位置
      // 1,取得坏字符的索引
      int badIndex = index + j;
      // 2,取得坏字符的模式中的位置
      int srcBadIndex = generBadCode[srcArrays[badIndex]];
      // 3，计算跳过的位数
      int justIndex = j - srcBadIndex;

      int goodSuffixIndex = 0;

      // 使用坏字符的索引做检查，如果坏字符索引小于最大长度，则做好后缀的检查
      if (j < findLength - 1) {
        // 使用好后缀的规则来进行跳过
        goodSuffixIndex = this.moveGoodSuffix(j, findLength, suffix, prefix);
      }

      index = index + Math.max(justIndex, goodSuffixIndex);
    }

    return -1;
  }

  /**
   * 通过模式串，构建坏字符的规则
   *
   * @param findArrays 坏字符信息
   * @return 坏字符的索引
   */
  public int[] generateBadCode(char[] findArrays) {

    int[] arrayIndex = new int[MAX_BYTE_SIZE];

    int length = findArrays.length;

    // 初始化坏字符集
    for (int i = 0; i < MAX_BYTE_SIZE; i++) {
      arrayIndex[i] = -1;
    }

    for (int i = 0; i < length; i++) {
      arrayIndex[findArrays[i]] = i;
    }

    return arrayIndex;
  }

  /**
   * 邓处理，好后缀的规则
   *
   * @param modeChars 模式串
   * @param suffix 好后缀的suffix数组，下标为最大可匹配的模式串的长度，值为下标的起始索引
   * @param prefix 以后缀串的长度为索引，值为是否与前缀串匹配，匹配为true,不匹配为false
   */
  private void generGoodSuffix(char[] modeChars, int[] suffix, boolean[] prefix) {
    int modeLength = modeChars.length;
    // 1,初始化数据集
    for (int i = 0; i < modeLength; i++) {
      suffix[i] = -1;
      prefix[i] = false;
    }

    // 开始构建坏字符集规则
    for (int i = 0; i < modeLength - 1; i++) {
      int j = i;
      int k = 0;

      // 用于进行后缀串前缀串的比较，顺序为从后向前进行
      while (j >= 0 && modeChars[j] == modeChars[modeLength - 1 - k]) {
        j--;
        k++;
      }

      // 如果当前k!=0，说明当前已经有好后缀 部分匹配
      if (k != 0) {
        suffix[k] = j + 1;
      }

      // 如果当前j <0 ,说明好后缀与前缀串，完全匹配，则标识为true
      if (j < 0) {
        prefix[k] = true;
      }
    }
  }

  private int moveGoodSuffix(int j, int modeLength, int[] suffix, boolean[] prefix) {
    // 1,计算好后缀的长度
    int goodSuffixLength = modeLength - 1 - j;

    // 检查好后缀是否在模式串中
    if (suffix[goodSuffixLength] != -1) {
      // 如果在械串中，直接计算跳过的位数
      return j - suffix[goodSuffixLength] + 1;
    }

    // 检查好后缀在模式串匹配前缀的最长字符
    for (int i = j + 2; i < modeLength - 1; i++) {

      if (prefix[i]) {
        return i;
      }
    }
    return modeLength;
  }
}
