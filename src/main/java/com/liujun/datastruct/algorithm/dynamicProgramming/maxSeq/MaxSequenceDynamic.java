package com.liujun.datastruct.algorithm.dynamicProgramming.maxSeq;

import java.util.Arrays;

/**
 * 计算最大递增子序列的长度，使用动态规划来解决
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/08
 */
public class MaxSequenceDynamic {

  public void countDynamic(int[] arrays) {
    int length = arrays.length;

    int[] status = new int[length];

    status[0] = 1;

    int commMax = 0;

    for (int i = 1; i < length; i++) {
      int max = 0;
      for (int j = 0; j < i; j++) {
        if (arrays[j] < arrays[i]) {
          if (status[j] > max) {
            max = status[j];
          }
        }
      }
      status[i] = max + 1;

      if (status[i] > commMax) {
        commMax = status[i];
      }
    }

    System.out.println("最大递增序列为 ：" + commMax);
    int maxComp = commMax;
    System.out.println("递增:" + Arrays.toString(status));

    for (int i = length - 1; i >= 0; i--) {
      if (status[i] == maxComp) {
        System.out.print("-->" + arrays[i]);
        maxComp = maxComp - 1;
      }
    }
  }
}
