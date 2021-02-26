package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code049;

import java.util.ArrayList;
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
public class Solution3 implements DataCountInf {

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

    // 数据影射
    int[] dataHash = {
      2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
      97, 101
    };

    // 集合记录下返回结果
    List<List<String>> resultList = new ArrayList<>(strs.length);
    // map以计算的索引值为key，以返回的结果集的索引的下标为值,避免扩容引起的开销
    Map<Long, Integer> dataMap = new HashMap<>(strs.length, 1);

    int putIndex = 0;
    for (int i = 0; i < strs.length; i++) {
      // 进行哈希计算
      long hashKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        char item = strs[i].charAt(j);
        hashKey *= dataHash[item - 97];
      }

      // 通过哈希计算得到索引
      Integer index = dataMap.get(hashKey);
      if (index != null) {
        resultList.get(index).add(strs[i]);
      }
      // 当此键不存在时,保存到索引中
      else {
        dataMap.put(hashKey, putIndex++);
        // 在结果集中加入一个
        List<String> subList = new ArrayList<>();
        subList.add(strs[i]);
        resultList.add(subList);
      }
    }

    return resultList;
  }
}
