package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code049;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 母异位词分组
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution5 implements DataCountInf {

  /** 用做哈希计算的字符 */
  private static final String HASH_PREFIX_DATA = "S";

  /**
   * 字母异位词分级
   *
   * @param strs
   * @return
   */
  @Override
  public List<List<String>> groupAnagrams(String[] strs) {
    if (null == strs || strs.length == 0) {
      return Collections.emptyList();
    }

    // 以排序过的字母为key，展位字符集为值
    int listIndex = 0;
    Map<Long, Integer> dataMap = new HashMap<>(strs.length, 1);
    List<List<String>> dataResult = new ArrayList<>(strs.length);
    for (int i = 0; i < strs.length; i++) {

      // 执行排序操作
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey = sumKey * Objects.hashCode(HASH_PREFIX_DATA + strs[i].charAt(j));
      }

      Integer dataIndex = dataMap.get(sumKey);
      if (null == dataIndex) {
        dataMap.put(sumKey, listIndex++);
        List<String> subList = new ArrayList<>();
        subList.add(strs[i]);
        dataResult.add(subList);
      } else {
        dataResult.get(dataIndex).add(strs[i]);
      }
    }

    return dataResult;
  }
}
