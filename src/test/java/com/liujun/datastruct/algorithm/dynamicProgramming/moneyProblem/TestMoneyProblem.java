package com.liujun.datastruct.algorithm.dynamicProgramming.moneyProblem;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/02
 */
public class TestMoneyProblem {

  @Test
  public void testRecursion() {
    int[] memoys = new int[] {1, 3, 5};

    MoneyProblem instance = new MoneyProblem();
    int minNum = instance.recursionMoney(memoys, 0, 0, 33);
    System.out.println("最小张数为:" + minNum);
  }
}
