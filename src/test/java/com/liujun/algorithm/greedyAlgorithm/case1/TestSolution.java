package com.liujun.algorithm.greedyAlgorithm.case1;

import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class TestSolution {

  @Test
  public void testGoods() {
    Solution instance = new Solution();
    instance.addGoods(new Goods("黄豆", 100, 100f));
    instance.addGoods(new Goods("绿豆", 30, 90f));
    instance.addGoods(new Goods("红豆", 60, 120f));
    instance.addGoods(new Goods("黑豆", 20, 80f));
    instance.addGoods(new Goods("青豆", 50, 75f));

    List<Goods> reslut = instance.getMaxValueGoos(100);

    for (Goods item : reslut) {
      System.out.println(item);
    }

    System.out.println("-----------------------------");

    List<Goods> reslut2 = instance.getMaxValueGoos(100);

    for (Goods item : reslut2) {
      System.out.println(item);
    }
  }
}
