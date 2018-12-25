package com.liujun.datastruct.algorithm.greedyAlgorithm.case3;

import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class TestSolutions {

  @Test
  public void testSolution() {
    Solutions instance = new Solutions();
    instance.addMemoryInfo("100元", 2, 100);
    instance.addMemoryInfo("50元", 2, 50);
    instance.addMemoryInfo("20元", 2, 20);
    instance.addMemoryInfo("10元", 2, 10);
    instance.addMemoryInfo("5元", 2, 5);
    instance.addMemoryInfo("2元", 2, 2);
    instance.addMemoryInfo("1元", 5, 1);

    List<MoneyBusi> list = instance.looseChange(332);
    for (MoneyBusi busi : list) {
      System.out.println(busi);
    }
  }
}
