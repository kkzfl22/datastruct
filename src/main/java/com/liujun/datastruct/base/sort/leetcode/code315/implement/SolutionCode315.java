package com.liujun.datastruct.base.sort.leetcode.code315.implement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/13
 */
public class SolutionCode315 {

  public List<Integer> countSmaller(int[] nums) {

    if (nums == null || nums.length == 0) {
      return new ArrayList<>(0);
    }

    List<Integer> result = new ArrayList<>(nums.length);

    if (nums.length < 2) {
      result.add(0);
      return result;
    }

    for (int i = 0; i < nums.length; i++) {
      int tmpItem = nums[i];
      int tmpCount = 0;
      for (int j = i + 1; j < nums.length; j++) {
        if (tmpItem > nums[j]) {
          tmpCount += 1;
        }
      }
      result.add(tmpCount);
    }

    return result;
  }
}
