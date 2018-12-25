package com.liujun.datastruct.algorithm.greedyAlgorithm.case3;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 零钱支付的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class Solutions {

  /** 用于存储金额的信息 */
  private PriorityQueue<MoneyBusi> moneyQueue =
      new PriorityQueue<>(
          (o1, o2) -> {
            if (o1.getMemory() < o2.getMemory()) {
              return 1;
            } else if (o1.getMemory() > o2.getMemory()) {
              return -1;
            }
            return 0;
          });

  /**
   * 添加金额信息
   *
   * @param value 面值信息
   * @param num 张数
   * @param memory 金额值
   */
  public void addMemoryInfo(String value, int num, int memory) {
    moneyQueue.offer(new MoneyBusi(value, num, memory));
  }

  /**
   * 计算找零钱的问题
   *
   * @param money 找零的金额信息
   * @return 找零钱的信息
   */
  public List<MoneyBusi> looseChange(int money) {

    List<MoneyBusi> resultMemory = new ArrayList<>();

    List<MoneyBusi> moreMemory = new ArrayList<>();

    int surplus = money;

    while (surplus > 0) {
      MoneyBusi busi = moneyQueue.peek();

      if (null != busi) {
        if (busi.getMemory() <= surplus) {
          busi = moneyQueue.poll();
          surplus = surplus - busi.getMemory();

          MoneyBusi busiNew = new MoneyBusi(busi.getValue(), 1, busi.getMemory());
          resultMemory.add(busiNew);

          busi.setNum(busi.getNum() - 1);

          if (busi.getNum() > 0) {
            moneyQueue.offer(busi);
          }
        } else {
          moreMemory.add(moneyQueue.poll());
        }
      } else {
        break;
      }
    }

    moneyQueue.addAll(moreMemory);

    return resultMemory;
  }
}
