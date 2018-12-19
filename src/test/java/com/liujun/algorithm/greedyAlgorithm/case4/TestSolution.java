package com.liujun.algorithm.greedyAlgorithm.case4;

import org.junit.Test;

import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class TestSolution {

  @Test
  public void testSolution() {
    Solution instance = new Solution();
    instance.add(6, 8);
    instance.add(2, 4);
    instance.add(3, 5);
    instance.add(1, 5);
    instance.add(5, 9);
    instance.add(8, 10);
    instance.add(4, 6);
    List<ScopeBusi> list = instance.countScope();

    System.out.println(list);
  }
}
