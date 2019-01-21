package com.liujun.datastruct.algorithm.dynamicProgramming.maxSeq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 计算最大递增子序列的长度，使用回塑的暴力搜索来解决下
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/08
 */
public class MaxSequence {

  public int max_length = Integer.MIN_VALUE;

  public void recursionCount(int[] arrays, int index, int maxSeq) {
    // 写出结果即为
    int maxLength = arrays.length;
    if (index == maxLength - 1) {
      if (max_length < maxSeq) {
        max_length = maxSeq;
      }
      return;
    }

    for (int i = index + 1; i < maxLength; i++) {
      if (arrays[index] < arrays[i]) {
        recursionCount(arrays, index + 1, maxSeq + 1);
      } else {
        recursionCount(arrays, index + 1, maxSeq);
      }
    }
  }

  public void recursionCount2(int[] arrays, int i) {

    if (i == arrays.length) {
      return;
    }

    int[] status = new int[arrays.length];

    int maxSeqNum = 0;
    boolean firstGet = true;
    int maxSeqValue = Integer.MAX_VALUE;
    for (int j = 0; j < i; j++) {
      // 1,查到比当前字符小的
      if (arrays[j] < arrays[i]) {
        if (firstGet) {
          maxSeqValue = arrays[j];
          firstGet = false;
          status[maxSeqNum] = arrays[j];
          maxSeqNum = maxSeqNum + 1;
          status[maxSeqNum] = arrays[i];
        } else {
          if (arrays[j] > maxSeqValue) {
            maxSeqValue = arrays[j];
            status[maxSeqNum] = arrays[j];
            maxSeqNum = maxSeqNum + 1;
            status[maxSeqNum] = arrays[i];
          }
        }
      }
    }

    System.out.println(Arrays.toString(status));

    status[i] = maxSeqNum;
    recursionCount2(arrays, i + 1);
  }

  /**
   * 使用动态规划解决此问题
   *
   * <p>2, 9, 3, 6, 5, 1, 7
   *
   * @param array
   * @return
   */
  public int longestIncreaseSubArrayDP(int[] array) {
    if (array.length < 2) return array.length;
    int[] state = new int[array.length];
    state[0] = 1;
    for (int i = 1; i < state.length; i++) {
      int max = 0;
      for (int j = 0; j < i; j++) {
        if (array[j] < array[i]) {
          if (state[j] > max) {
            max = state[j];
          }
        }
      }
      state[i] = max + 1;
    }
    int result = 0;
    for (int i = 0; i < state.length; i++) {
      if (state[i] > result) result = state[i];
    }
    return result;
  }

  public int[] status = new int[20];

  /**
   * 使用递归加备忘录的方式来进行求解
   *
   * @param arrays
   * @param i
   */
  public void recursionCount3(int[] arrays, int i) {

    if (i == arrays.length) {
      return;
    }

    if (i == 1) {
      status[0] = 1;
    }

    int max = 0;

    for (int j = 0; j < i; j++) {
      if (arrays[j] < arrays[i]) {
        if (status[j] > max) {
          max = status[j];
        }
      }
      status[i] = max + 1;
    }

    recursionCount3(arrays, i + 1);
  }

  /**
   * 使用递归来进行求解
   *
   * @param arrays
   */
  public int recursionCount4(int[] arrays, int index) {

    if (index == 0) {
      return 1;
    }
    int max = 0;
    //此问题的解，递归的核心就是在之前的序列中找到最大递增子序列加1
    //所以需要遍历此此之前的全部数据项
    for (int i = 0; i < index; i++) {
      //递归求解每项的最递增序列
      int value = recursionCount4(arrays, i);
      if (arrays[i] < arrays[index]) {
        if (value > max) {
          max = value;
        }
      }
    }

    return max + 1;
  }
}
