package com.liujun.datastruct.base.datastruct.recursion.leetcode.code79.implement;

/**
 * 进行字符串的在二维数组中的查找匹配
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/26
 */
public class SolutionRectustionMySelf {

  public boolean exist(char[][] src, String data) {

    boolean[][] exists = new boolean[src.length][src[0].length];

    boolean existsResult = false;

    String srcvalue = null;

    //搜索开始的字符位置
    for (int i = 0; i < src.length; i++) {
      for (int j = 0; j < src[i].length; j++) {
        srcvalue = String.valueOf(src[i][j]);
        if (data.startsWith(srcvalue)) {
          existsResult = this.exist(src, "", data, i, j, exists);

          if (existsResult) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * 每个字符的匹配可以看作是当前字符与其他字体的一个匹配
   *
   * @param src 原始的匹配字符数组
   * @param currData 当前的字符
   * @param targetData 匹配的目标字符
   * @param rowindex 行索引
   * @param cellindex 列索引
   * @return
   */
  public boolean exist(
      char[][] src,
      String currData,
      String targetData,
      int rowindex,
      int cellindex,
      boolean[][] exists) {

    if (currData.equals(targetData)) {
      return true;
    }

    if (rowindex < 0 || rowindex >= src.length) {
      return false;
    }

    if (cellindex < 0 || cellindex >= src[rowindex].length) {
      return false;
    }

    // 节点已经访问，则标识为false
    if (exists[rowindex][cellindex]) {
      return false;
    }

    currData = currData + src[rowindex][cellindex];

    if (currData.length() > targetData.length()) {
      return false;
    }

    // 在进入时标识已经访问
    exists[rowindex][cellindex] = true;

    // 当字符匹配的时，可能存在4个方向的情况
    if (targetData.startsWith(currData)) {
      if (exist(src, currData, targetData, rowindex + 1, cellindex, exists)
          || exist(src, currData, targetData, rowindex, cellindex + 1, exists)
          || exist(src, currData, targetData, rowindex - 1, cellindex, exists)
          || exist(src, currData, targetData, rowindex, cellindex - 1, exists)) {
        return true;
      }
    }
    // 退出时还需要标识出未访问
    exists[rowindex][cellindex] = false;

    return false;
  }
}
