package com.liujun.math.chapter02Iterate;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestGetNumForWheat {

  @Test
  public void testWheatFor() {
    GetNumForWheat instance = new GetNumForWheat();
    instance.countWithFor();
  }

  @Test
  public void testCountrecurstion() {
    GetNumForWheat instance = new GetNumForWheat();
    long sumValue = instance.countRecursion(1, 1, 1, 63);
    System.out.println("计算的结果:" + sumValue);
  }
}
