package com.liujun.datastruct.base.sort.leetcode.code349.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 求两个数据的交集
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/26
 */
public class SolutionIntersection {
  private static final int NUM_ONE = 1;

  private static final int NUM_TWO = 2;

  public int[] intersection(int[] nums1, int[] nums2) {
    int dataCountLength = nums1.length;

    Map<Integer, Integer> dataCount = new HashMap<>(dataCountLength);

    // 遍历数组1，将基础的元素记录下来
    for (Integer valueItem : nums1) {
      if (!dataCount.containsKey(valueItem)) {
        dataCount.put(valueItem, NUM_ONE);
      }
    }
    for (Integer item2 : nums2) {
      if (dataCount.containsKey(item2)) {
        dataCount.put(item2, NUM_TWO);
      }
    }
    List<Integer> outdataList = new ArrayList<>();

    for (Map.Entry<Integer, Integer> itemData : dataCount.entrySet()) {
      if (itemData.getValue() == NUM_TWO) {
        outdataList.add(itemData.getKey());
      }
    }

    int[] outdata = new int[outdataList.size()];

    for (int i = 0; i < outdataList.size(); i++) {
      outdata[i] = outdataList.get(i);
    }

    return outdata;
  }
}
