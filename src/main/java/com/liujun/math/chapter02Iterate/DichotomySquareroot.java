package com.liujun.math.chapter02Iterate;

/**
 * 使用二分法进行平方根的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class DichotomySquareroot {

  /**
   * 计算平方根的求解
   *
   * @param value
   */
  public void countSquareRoot(int value) {
    double start = 0;
    double end = value;
    double mid = start + (end - start) / 2;

    // 最大计算次数，避免不必要的计算浪费
    int maxCountNum = 64;
    int countIndex = 0;

    // 控制求解次数
    while (countIndex < maxCountNum) {
      // 开始二分法的求解
      if (mid * mid == value) {
        break;
      }
      // 1,如果当前平台大于求值，在左区间中查找
      else if (mid * mid > value) {
        end = mid;
      }
      // 如果小于值，则在右区间中查找
      else {
        start = mid;
      }
      mid = start + (end - start) / 2;

      countIndex++;
    }

    System.out.println("求解的值为 ：" + mid);
  }

  /**
   * 求平方根，如果不能求得完整解，求近似解
   *
   * @param n 求解的数字
   * @param deltaThreshold 误差的阈值,近似解的精度,比如0.00001
   * @param maxTry 最大求解次数
   * @return 最终的求解结果
   */
  public double getSqureRoot(int n, double deltaThreshold, int maxTry) {

    if (n <= 1) {
      return -1.0;
    }

    double min = 0;
    double max = (double) n;
    double mid = 0;
    for (int i = 0; i < maxTry; i++) {
      mid = min + (max - min) / 2;

      double square = mid * mid;

      double delta = Math.abs((square / n) - 1);

      if (delta <= deltaThreshold) {
        return mid;
      } else {
        if (square > n) {
          max = mid;
        } else {
          min = mid;
        }
      }
    }

    return -2d;
  }
}
