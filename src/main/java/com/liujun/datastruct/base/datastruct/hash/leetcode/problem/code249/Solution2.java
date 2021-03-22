package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code249;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行结果： 通过
 *
 * <p>显示详情 执行用时： 2 ms ,
 *
 * <p>在所有 Java 提交中击败了 89.14% 的用户
 *
 * <p>内存消耗： 38.5 MB , 在所有 Java 提交中击败了 86.24% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution2 {

  private static final int DATA = 26;

  public List<List<String>> groupStrings(String[] strings) {

    if (strings == null || strings.length < 1) {
      return Collections.emptyList();
    }

    // 1,按字符的长度进行分组操作
    Map<String, List<String>> groupMap = new HashMap<>(strings.length, 1);

    for (int i = 0; i < strings.length; i++) {
      String key = getKey(strings[i]);
      List<String> dataList = groupMap.get(key);
      if (dataList == null) {
        dataList = new ArrayList<>();
        groupMap.put(key, dataList);
      }
      dataList.add(strings[i]);
    }

    List<List<String>> dataResult = new ArrayList<>(groupMap.size());
    for (Map.Entry<String, List<String>> dataItem : groupMap.entrySet()) {
      dataResult.add(dataItem.getValue());
    }
    return dataResult;
  }

  /**
   * 以偏移量做key
   *
   * @param item
   * @return
   */
  private String getKey(String item) {

    // 单字符使用统一的key
    if (item.length() <= 1) {
      return "-1";
    }

    StringBuilder sumKey = new StringBuilder(item.length() * 2);

    for (int j = 1; j < item.length(); j++) {
      //  'z' 的后续为 'a'，所以 ["az","ba"] 也满足 “移位” 操作规律。
      // 此使用求模操作。作为一个环来计算
      sumKey.append((item.charAt(j) - item.charAt(j - 1) + DATA) % DATA);
    }
    return sumKey.toString();
  }
}
