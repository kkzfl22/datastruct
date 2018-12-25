package com.liujun.datastruct.datastruct.charMatch.difficulty.kmp;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/12
 */
public class Kmp {

  public int[] getNext(char[] modeChars) {
    int modeLength = modeChars.length;

    int[] result = new int[modeLength];
    result[0] = -1;

    int k = -1;
    for (int i = 1; i < modeLength; i++) {
      while (k >= 0 && modeChars[k + 1] != modeChars[i]) {
        k = result[k];
      }

      if (modeChars[i] == modeChars[k + 1]) {
        k++;
      }
      result[i] = k;
    }

    return result;
  }

  /**
   * 使用kmp算法进行字符串的查找
   *
   * @param src 主串
   * @param find 模式串
   * @return 模式串在主串中的下标
   */
  public int kmp(String src, String find) {
    char[] srcArrays = src.toCharArray();
    char[] modeArrays = find.toCharArray();

    // 通过模式串构建next数组
    int[] nexts = this.getNext(modeArrays);

    int slength = src.length();
    int mlength = modeArrays.length;

    int j = 0;

    for (int i = 0; i < slength; i++) {
      // 进行从前向后匹配，当发生不匹配的时间，将模式串多滑动几位
      while (j > 0 && srcArrays[i] != modeArrays[j]) {
        j = nexts[j - 1] + 1;
      }

      if (srcArrays[i] == modeArrays[j]) {
        j++;
      }

      if (j == mlength) {
        return i - mlength + 1;
      }
    }

    return -1;
  }

  private String print(char[] modeChars, int start, int end) {
    String result = "";
    for (int i = start; i <= end; i++) {
      result += modeChars[i];
    }
    return result;
  }

  public static void main(String[] args) {
    Kmp instance = new Kmp();

    String src = "this is name ababacd";

    String suffix = "ababacd";

    int[] asc2 = instance.getNext(suffix.toCharArray());
    // System.out.println(Arrays.toString(asc2));

    for (int i = 0; i < asc2.length - 1; i++) {
      System.out.print(
          "好前缀:" + suffix.substring(0, i + 1) + ",前缀结尾字符下标:" + i + ",最长可匹配前缀字符结尾下标:" + asc2[i]);
      System.out.println();
    }

    int index = instance.kmp(src, suffix);
    System.out.println("查找的位置 ：" + index);
    if (index != -1) {
      System.out.println(src.substring(index, index + suffix.length()));
    }
  }
}
