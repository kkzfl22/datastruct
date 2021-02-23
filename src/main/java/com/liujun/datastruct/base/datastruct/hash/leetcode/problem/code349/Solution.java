package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code349;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public int[] intersection(int[] nums1, int[] nums2) {

    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }

    Set<Integer> set = new HashSet<>(nums1.length);
    for (int dataItem : nums1) {
      set.add(dataItem);
    }

    Set<Integer> dataList = new HashSet<>(nums2.length);
    for (int numsItem2 : nums2) {
      if (set.contains(numsItem2)) {
        dataList.add(numsItem2);
      }
    }

    int[] dataResult = new int[dataList.size()];
    int index = 0;
    for (int dataItem : dataList) {
      dataResult[index] = dataItem;
      index++;
    }

    return dataResult;
  }
}
