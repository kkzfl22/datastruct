package com.liujun.datastruct.algorithm.greedyAlgorithm.case1;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 用于计算在重量100的前提下，价值最大化
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class Solution {

  private PriorityQueue<Goods> GoodsList =
      new PriorityQueue<>(
          (o1, o2) -> {
            if (o1.getUnitPrice() < o2.getUnitPrice()) {
              return 1;
            } else if (o1.getUnitPrice() > o2.getUnitPrice()) {
              return -11;
            }
            return 0;
          });

  public void addGoods(Goods gs) {
    this.GoodsList.add(gs);
  }

  /**
   * 获取物品价值最大化的方法
   *
   * @param weight
   * @return
   */
  public List<Goods> getMaxValueGoos(int weight) {
    List<Goods> list = new ArrayList<>();

    // 首先将初始化为最大重量
    int surplus = weight;

    while (!GoodsList.isEmpty()) {
      Goods goodItem = GoodsList.poll();

      if (surplus >= goodItem.getWight()) {
        surplus = surplus - goodItem.getWight();
        list.add(goodItem);
      } else {
        int surplusVals = goodItem.getWight() - surplus;
        goodItem.setWight(surplusVals);
        GoodsList.offer(goodItem);
        Goods result =
            new Goods(goodItem.getName(), surplus, goodItem.getValue(), goodItem.getUnitPrice());
        list.add(result);
        break;
      }
    }

    return list;
  }
}
