package com.liujun.datastruct.algorithm.dynamicProgramming.moneyProblem;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/02
 */
public class TestMoneyProblemDynamic {

  @Test
  public void testRecursion() {
    int[] memoys = new int[] {1, 2, 5};

    DynmicProgammingSolution instance = new DynmicProgammingSolution();
    int minNum = instance.countMoneyMin(memoys, 7);
    System.out.println("最小张数为:" + minNum);
  }
}
