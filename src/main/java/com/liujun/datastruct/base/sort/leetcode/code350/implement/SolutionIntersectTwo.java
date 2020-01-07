package com.liujun.datastruct.base.sort.leetcode.code350.implement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 按交集的顺序输出，需要额外的来声明操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/26
 */
public class SolutionIntersectTwo {

  private static final int VAL_USE = 1;

  public int[] intersect(int[] nums1, int[] nums2) {

    Map<Integer, Integer> dataCountMap = new LinkedHashMap<>(nums1.length);

    for (int i = 0; i < nums1.length; i++) {
      if (!dataCountMap.containsKey(nums1[i])) {
        dataCountMap.put(nums1[i], VAL_USE);
      } else {
        dataCountMap.put(nums1[i], dataCountMap.get(nums1[i]) + VAL_USE);
      }
    }

    List<Integer> dataOut = new ArrayList<>(nums2.length);

    for (int i = 0; i < nums2.length; i++) {
      if (dataCountMap.containsKey(nums2[i])) {
        dataOut.add(nums2[i]);
        dataCountMap.put(nums2[i], dataCountMap.get(nums2[i]) - 1);
        if (dataCountMap.get(nums2[i]) == 0) {
          dataCountMap.remove(nums2[i]);
        }
      }
    }

    int[] outIntValue = new int[dataOut.size()];

    for (int i = 0; i < dataOut.size(); i++) {
      outIntValue[i] = dataOut.get(i);
    }

    return outIntValue;
  }
}
