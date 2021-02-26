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
 * <p>最简单的解法，进行字符排序
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
public class Solution implements DataCountInf {

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
    Map<String, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {
      char[] dataItem = strs[i].toCharArray();
      // 执行排序操作
      Arrays.sort(dataItem);

      String key = new String(dataItem);
      List<String> dataSameList = dataMap.get(key);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(key, dataSameList);
      }
      dataSameList.add(strs[i]);
    }

    // 转换结果输出
    List<List<String>> dataList = new ArrayList<>(dataMap.size());
    for (Map.Entry<String, List<String>> dataItem : dataMap.entrySet()) {
      dataList.add(dataItem.getValue());
    }

    return dataList;
  }
}
