package com.liujun.datastruct.algorithm.greedyAlgorithm.case2;

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
    instance.addSweet("大糖果", 1, 30);
    instance.addSweet("中糖果", 2, 20);
    instance.addSweet("小糖果", 2, 10);

    instance.addChild("小明", 30);
    instance.addChild("小明2", 30);
    instance.addChild("小红", 20);
    instance.addChild("小绿", 20);
    instance.addChild("小紫", 20);
    instance.addChild("小甲", 10);
    instance.addChild("小乙", 10);
    instance.addChild("小丙", 10);

    List<Child> list = instance.alloc();

    for (Child chil : list) {
      System.out.println(chil);
    }
  }
}
