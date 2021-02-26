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
 * <p>使用求哈希值的方式，进行做乘法操作,缺点是计算量大
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution4 implements DataCountInf {

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

    // 以乘积做为key，以异位字符集做值
    Map<Long, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {

      // 计算乘积
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey = sumKey * Objects.hashCode(HASH_PREFIX_DATA + strs[i].charAt(j));
      }

      // 保存结果
      List<String> dataSameList = dataMap.get(sumKey);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(sumKey, dataSameList);
      }
      dataSameList.add(strs[i]);
    }

    // 转换结果输出
    List<List<String>> dataList = new ArrayList<>(dataMap.size());
    for (Map.Entry<Long, List<String>> dataItem : dataMap.entrySet()) {
      List<String> value = dataItem.getValue();
      dataList.add(value);
    }

    return dataList;
  }

  /**
   * 进行数据的检查操作
   *
   * @param value 数据项
   */
  private boolean check(List<String> value) {
    String valueKey = sortArray(value.get(0));
    for (int i = 1; i < value.size(); i++) {
      String itemValue = sortArray(value.get(i));
      if (!valueKey.equals(itemValue)) {
        return false;
      }
    }
    return true;
  }

  private String sortArray(String item) {
    char[] arrayItem = item.toCharArray();
    Arrays.sort(arrayItem);
    return new String(arrayItem);
  }
}
