package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code599;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * code 599. 两个列表的最小索引总和
 *
 * <p>执行结果： 通过 显示详情
 *
 * <p>执行用时： 11 ms ,
 *
 * <p>在所有 Java 提交中击败了 55.15% 的用户
 *
 * <p>内存消耗： 39.4 MB , 在所有 Java 提交中击败了 9.42% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class SolutionTop1 {

  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list1.length == 0 || list2 == null || list2.length == 0) {
      return new String[] {};
    }
    if (list1.length > list2.length) {
      return findRestaurant(list2, list1);
    }
    Map<String, Integer> hash = new HashMap<>(list1.length, 1);
    List<String> list = new ArrayList<>();
    for (int i = 0; i < list1.length; i++) {
      hash.put(list1[i], i);
    }
    int minIndexSum = Integer.MAX_VALUE;
    for (int j = 0; j < list2.length; j++) {
      String restaurant = list2[j];
      Integer i = hash.get(restaurant);
      if (i != null) {
        int sum = i + j;
        if (sum <= minIndexSum) {
          if (sum < minIndexSum) {
            minIndexSum = sum;
            list.clear();
          }
          list.add(restaurant);
        }
      }
      if (list.size() > 0 && minIndexSum <= j) {
        break;
      }
    }

    String[] dataRsp = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      dataRsp[i] = list.get(i);
    }
    return dataRsp;
  }
}
