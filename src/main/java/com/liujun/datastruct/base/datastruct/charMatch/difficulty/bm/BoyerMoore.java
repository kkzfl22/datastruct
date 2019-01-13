package com.liujun.datastruct.base.datastruct.charMatch.difficulty.bm;

/**
 * BM算法，用来进行高效的字符串查找
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class BoyerMoore {

  private static final int BYTE_SIZE = 256;

  /**
   * 生成散列表，用来记录模式器所对应的位置，
   *
   * <p>相同的模式串，仅记录下最后的位置
   *
   * @param modChar 模式串
   * @return 生成的模式串所对应的hashCode
   */
  private int[] generateCode(char[] modChar) {
    int[] bchashs = new int[BYTE_SIZE];

    for (int i = 0; i < BYTE_SIZE; i++) {
      bchashs[i] = -1;
    }

    for (int i = 0; i < modChar.length; i++) {
      bchashs[modChar[i]] = i;
    }

    return bchashs;
  }

  public int bm(String src, String find) {
    char[] srcArrays = src.toCharArray();
    char[] findArrays = find.toCharArray();

    int n = srcArrays.length;
    int m = findArrays.length;

    // 构建模式串，记录下每个字符最后出现的位置
    int[] bcCodes = this.generateCode(findArrays);

    // 计算suffix数据
    int[] suffix = new int[m];
    boolean[] prefix = new boolean[m];
    // 进行规则需的数据计算
    this.generatsGS(findArrays, suffix, prefix);

    // 主串与模式串对齐的第一个字符
    int i = 0;

    while (i <= n - m) {
      int j;
      for (j = m - 1; j >= 0; j--) {
        if (srcArrays[i + j] != findArrays[j]) {
          break;
        }
      }

      if (j < 0) {
        return i;
      }
      // 1,srcArrays[i + j]标识当前坏字符串的位置

      // 2,我们把坏字符串在模式串的位置记作si,当前使用j表示

      // 3,然后去模式串中查找坏字符串出现的最后位置记作xi,当前为 bcCodes[srcArrays[i + j]]

      // 4,命名用si-xi，即为需要移动的位数，所以即为j-bcCodes[srcArrays[i + j]]

      // 计算向后滑动的位数,先不考虑为负的情况
      int badCount = (j - bcCodes[srcArrays[i + j]]);

      int y = 0;

      if (j < m - 1) {
        y = moveGs(j, m, suffix, prefix);
      }

      i = i + Math.max(badCount, y);
    }
    return -1;
  }

  /**
   * @param j 坏字符在模式串的位置
   * @param m 模式串的长度
   * @param suffix 好后缀长度对应的起始索引
   * @param prefix 是否与前缀字符匹配
   * @return
   */
  public int moveGs(int j, int m, int[] suffix, boolean[] prefix) {
    // 好后缀的长度
    int k = m - 1 - j;
    // 规则1匹配,在模式串中，查找跟好后缀匹配的另一个子串；
    if (suffix[k] != -1) {
      return j - suffix[k] + 1;
    }

    // 第二条规则匹配,在好后缀的后缀子串中，查找最长的、能跟模式串前缀子串匹配的后缀子串；
    for (int i = j + 2; i < m - 1; i++) {
      if (prefix[m - i + 1]) {
        return i;
      }
    }

    return j + 1;
  }

  /**
   * 用来计算 suffix 给你个prefix数组的信息
   *
   * <p>• 在模式串中，查找跟好后缀匹配的另一个子串；
   *
   * <p>• 在好后缀的后缀子串中，查找最长的、能跟模式串前缀子串匹配的后缀子串；
   *
   * @param findChar 模式串信息
   * @param suffix 后缀子串在前缀子串中的起始下标
   * @param prefix 后缀子串是否能匹配前缀子串
   */
  public void generatsGS(char[] findChar, int[] suffix, boolean[] prefix) {

    // 初始化两个数组
    int length = findChar.length;

    for (int i = 0; i < length; i++) {
      suffix[i] = -1;
      prefix[i] = false;
    }

    // 计算填充两个模式串中的信息
    for (int i = 0; i < length - 1; i++) {
      int j = i;
      // 公共后缀子串的长度
      int k = 0;

      while (j >= 0 && findChar[j] == findChar[length - 1 - k]) {
        --j;
        ++k;
      }

      if (k != 0) {
        // j+1表示公共后缀子串在b[0,i]中的起始下标
        suffix[k] = j + 1;
      }

      // 如果公共后缀子串也是模式串的前缀子串
      if (j == -1) {
        prefix[k] = true;
      }
    }
  }
}
