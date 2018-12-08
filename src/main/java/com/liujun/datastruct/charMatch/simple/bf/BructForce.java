package com.liujun.datastruct.charMatch.simple.bf;

/**
 * BF字符串匹配算法，也叫暴力匹配算法
 *
 * <p>时间复杂度为O(n*m)
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/08
 */
public class BructForce {

  /**
   * 使用bf算法进进行字符串匹配搜索，
   *
   * @param src 源字符串
   * @param find 匹配的字符串
   * @return 返回查找字符串开始的下标，如果未找到返回-1
   */
  public int find(String src, String find) {

    if (null == src || find == null) {
      return -1;
    }

    char[] srcArrays = src.toCharArray();
    char[] findArrays = find.toCharArray();

    int srcStartIndex = 0;
    int findIndex = -1;

    // 主串的遍历
    while (srcStartIndex < srcArrays.length) {
      // 如果遍历到尾都没有找到，则结束查找
      if (srcStartIndex + findArrays.length > srcArrays.length) {
        break;
      }

      findIndex = -1;

      // 模式串的遍历
      for (int i = 0; i < findArrays.length; i++) {
        if (srcArrays[srcStartIndex + i] == findArrays[i]) {
          if (findIndex == -1) {
            findIndex = srcStartIndex + i;
          }
          continue;
        } else {
          findIndex = -1;
          break;
        }
      }

      // 如果索引被找到则直接返回
      if (findIndex != -1) {
        return findIndex;
      }

      srcStartIndex++;
    }

    return -1;
  }
}
