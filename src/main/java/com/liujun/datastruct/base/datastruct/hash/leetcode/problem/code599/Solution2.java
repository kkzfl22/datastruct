package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code599;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * code 599. 两个列表的最小索引总和
 *
 * <p>执行结果： 通过 显示详情
 *
 * <p>执行用时： 5 ms ,
 *
 * <p>在所有 Java 提交中击败了 99.76% 的用户
 *
 * <p>内存消耗： 39.3 MB , 在所有 Java 提交中击败了 17.52% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list2 == null) {
      return new String[0];
    }

    // 使用map来求解，list1的索引和记录,阈值设置为1，避免扩容
    Map<String, Integer> srcMap = new HashMap<>(list1.length, 1);
    for (int i = 0; i < list1.length; i++) {
      srcMap.put(list1[i], i);
    }

    Integer sumMin = null;
    List<String> continueList = new ArrayList<>();
    int minSum;

    for (int i = 0; i < list2.length; i++) {
      if (srcMap.containsKey(list2[i])) {
        minSum = srcMap.get(list2[i]);
        int key = i + minSum;
        if (sumMin == null) {
          sumMin = key;
        }
        // 当数据比已知的最小数据大时，则跳过处理
        else if (key > sumMin) {
          continue;
        }
        // 如果找到比当前小的，则清空数据集，重新加入
        else if (key < sumMin) {
          sumMin = key;
          continueList.clear();
        }

        if (key == sumMin) {
          continueList.add(list2[i]);
        }

        // 如果当前最小的数，小于或者等于索引，则可以返回
        if (!continueList.isEmpty() && sumMin <= i) {
          break;
        }
      }
    }

    String[] dataRsp = new String[continueList.size()];
    for (int i = 0; i < continueList.size(); i++) {
      dataRsp[i] = continueList.get(i);
    }
    return dataRsp;
  }
}
