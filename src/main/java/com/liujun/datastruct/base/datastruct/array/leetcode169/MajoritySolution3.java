package com.liujun.datastruct.base.datastruct.array.leetcode169;

import java.util.HashMap;
import java.util.Map;

/**
 * 求众数的，第三种方法求解，
 *
 * <p>使用多数投票算法
 *
 * <p>基于一个已成结束的事实，就是有一个存在超过半数数字
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/26
 */
public class MajoritySolution3 {

  public int majorityElement(int[] nums) {

    int tmpData = nums[0];
    int vote = 1;

    for (int i = 1; i < nums.length; i++) {
      if (vote == 0) {
        tmpData = nums[i];
        vote++;
      } else if (tmpData == nums[i]) {
        vote++;
      } else {
        vote--;
      }
    }

    return tmpData;
  }
}
