package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code049;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 母异位词分组
 *
 * <p>执行结果： 通过 显示详情
 *
 * <p>执行用时： 7 ms ,
 *
 * <p>在所有 Java 提交中击败了 95.63% 的用户
 *
 * <p>内存消耗： 41.7 MB , 在所有 Java 提交中击败了 37.61% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 implements DataCountInf {

  /**
   * 字母异位词分级
   *
   * @param strs
   * @return
   */
  @Override
  public List<List<String>> groupAnagrams(String[] strs) {

    if (null == strs || strs.length < 1) {
      return Collections.emptyList();
    }

    // 建立hash影射关系,使用质数
    int[] hash = {
      2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
      97, 101
    };

    // 求字母异位词
    Map<Long, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {

      // 进行hash值的计算操作
      long hashKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        char item = strs[i].charAt(j);
        int index = item - 97;

        hashKey *= hash[index];
      }

      // 将异位字符加入结果集中
      List<String> dataSameList = dataMap.get(hashKey);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(hashKey, dataSameList);
      }
      dataSameList.add(strs[i]);
    }

    // 转换结果输出
    List<List<String>> dataList = new ArrayList<>(dataMap.size());
    for (Map.Entry<Long, List<String>> dataItem : dataMap.entrySet()) {
      dataList.add(dataItem.getValue());
    }

    return dataList;
  }
}
