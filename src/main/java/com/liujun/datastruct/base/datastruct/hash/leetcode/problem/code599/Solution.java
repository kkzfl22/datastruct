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
 * <p>执行用时： 11 ms ,
 *
 * <p>在所有 Java 提交中击败了 55.15% 的用户
 *
 * <p>内存消耗： 39.4 MB , 在所有 Java 提交中击败了 9.42% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list2 == null) {
      return new String[0];
    }

    // 使用map记录下索引号
    Map<String, Integer> srcMap = new HashMap<>(list1.length);
    // 填充下索引号
    for (int i = 0; i < list1.length; i++) {
      srcMap.put(list1[i], i);
    }

    // 使用有序map来记录下，有序和相同的数据集
    TreeMap<Integer, List<String>> dataMap = new TreeMap<>();
    Integer minSum;
    for (int i = 0; i < list2.length; i++) {
      //首先元素必须在输入1中
      minSum = srcMap.get(list2[i]);
      if (null == minSum) {
        continue;
      }

      // 计算索引和
      int key = i + minSum;
      // 使用索引和作为key去查找
      List<String> continueList = dataMap.get(key);
      // 当数据不存在时，则加入
      if (null == continueList) {
        continueList = new ArrayList<>();
        dataMap.put(key, continueList);
      }
      continueList.add(list2[i]);
    }

    List<String> dataList = dataMap.get(dataMap.firstKey());
    String[] dataRsp = new String[dataList.size()];
    for (int i = 0; i < dataList.size(); i++) {
      dataRsp[i] = dataList.get(i);
    }

    return dataRsp;
  }
}
