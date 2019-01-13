package com.liujun.datastruct.base.datastruct.recursion;

/**
 * 求台阶数走法的问题，设现在有N个台阶，每次只能走1步或者2步，求有多少种走法?
 *
 * <p>这个问题可以求解为第一步走1阶的走法，加上第一步走2阶的走法，
 *
 * <p>中止条件，即最后一次，发生的可能性，一步或者两步都不能再继续了
 *
 * <p>递归公式就是f(n) = f(n-1) + f(n-2)
 *
 * <p>中止条件为f(n)=1或者f(n)=2,两阶有两种走法么,常数
 *
 * <p>改写为非递归代码
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/26
 */
public class RecursionNotStep {

  public static final RecursionNotStep INSTANCE = new RecursionNotStep();

  private int count;

  public int step(int n) {

    count++;

    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }

    return step(n - 1) + step(n - 2);
  }

  public static void main(String[] args) {
    int vaue = RecursionNotStep.INSTANCE.step(6);
    System.out.println(vaue);

    System.out.println("count num:" + RecursionNotStep.INSTANCE.count);

    System.out.println(RecursionNotStep.INSTANCE.countNotRecursion(6));
  }

  /**
   * 梯归代码改写为非梯归代码，原理同梯归类似，即为前一个值的走法加上再前前一个值的走法
   *
   * @param ns
   * @return
   */
  public int countNotRecursion(int ns) {
    if (ns == 1) {
      return 1;
    }
    if (ns == 2) {
      return 2;
    }
    int result = 0;

    int currTopNum = 2;

    int currTopTopNum = 1;

    for (int i = 3; i <= ns; i++) {
      result = currTopNum + currTopTopNum;
      currTopTopNum = currTopNum;
      currTopNum = result;
    }

    return result;
  }
}
