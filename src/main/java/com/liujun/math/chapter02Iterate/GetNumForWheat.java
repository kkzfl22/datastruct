package com.liujun.math.chapter02Iterate;

/**
 * 计算填满整个棋盘需要多少麦子
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class GetNumForWheat {

  /** 使用循环语句来计算结果 */
  public void countWithFor() {
    int maxTry = 64;

    // 开始的麦子数
    long start = 1;

    // 计算总的麦子数
    long sumWheat = start;
    long countValue = start;

    // 每一个棋格是前一个的2倍
    for (int i = 2; i < maxTry; i++) {
      countValue = countValue * 2;
      sumWheat = sumWheat + countValue;
    }

    System.out.println("总共麦子数" + sumWheat);
  }

  /**
   * 使用递归进行求解棋盘中的麦粒数
   *
   * @param start 开始数
   * @param sum 结果
   * @param curIndex 当前索引号
   * @param maxNum 最大数
   * @return 求解的棋盘的最终麦粒数
   */
  public Long countRecursion(long start, long sum, int curIndex, int maxNum) {
    if (curIndex >= maxNum) {
      return sum;
    }

    sum = sum + start * 2;

    // 继续递归求解
    return countRecursion(start * 2, sum, curIndex + 1, maxNum);
  }
}
