package com.liujun.math.chapter02Iterate;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestDichotomySquareroot {

  @Test
  public void countSquareRoot() {
    DichotomySquareroot instance = new DichotomySquareroot();
    instance.countSquareRoot(11);
  }

  @Test
  public void countSquareRoot2() {
    DichotomySquareroot instance = new DichotomySquareroot();
    double vaoue = instance.getSqureRoot(11, 0.01, 32);
    System.out.println("求解的最终值为:" + vaoue);
  }
}
