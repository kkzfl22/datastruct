package com.liujun.math.chapter04Conclude;

import com.liujun.math.chapter02Iterate.GetNumForWheat;

/**
 * 通过对比麦粒数据的计算，对比归纳与上一章节的迭代法的对比
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class WheatCount {

  private GetNumForWheat instance = new GetNumForWheat();

  /** 迭代法统计 */
  private long useTimeIterator = 0;

  /** 归纳法统计 */
  private long useTimeConcolude = 0;

  /** 使用迭代法进行统计 */
  public void wheatCountIterate() {
    System.out.println("迭代法计算-----");
    long startTime = System.nanoTime();
    long sumValue = instance.countRecursion(1, 1, 1, 63);
    System.out.println("结果:" + sumValue);
    long endTime = System.nanoTime();
    useTimeIterator = endTime - startTime;
    System.out.println("用时:" + (useTimeIterator));
    timeUse();
  }

  /** 使用归纳法总结后，直接计算 */
  public void wheatCountConclude() {
    System.out.println("归纳法计算-----");

    long startTime = System.nanoTime();

    long sumValue = (long) Math.pow(2, 63) - 1;
    System.out.println("结果:" + sumValue);

    long endTime = System.nanoTime();
    useTimeConcolude = endTime - startTime;
    System.out.println("用时:" + useTimeConcolude);
    timeUse();
  }

  public void timeUse() {
    if (useTimeConcolude != 0 && useTimeIterator != 0) {
      System.out.println("两个时间差为:" + (double) useTimeIterator / useTimeConcolude);
    }
  }
}
