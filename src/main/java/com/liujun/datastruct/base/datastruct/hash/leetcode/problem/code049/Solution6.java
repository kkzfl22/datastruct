package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code049;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

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
 * <p>使用新的hash函数计算
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution6 implements DataCountInf {

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

    // 以计算的乘法结果为key，异位词集为值
    Map<Long, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {
      char[] dataItem = strs[i].toCharArray();

      // 乘积计算
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey *= (strs[i].charAt(j));
      }

      // 记录保存至map中
      String key = new String(dataItem);
      List<String> dataSameList = dataMap.get(key);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(sumKey, dataSameList);
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
