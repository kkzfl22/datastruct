package com.liujun.datastruct.base.sort.leetcode.code57.implement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/06
 */
public class SolutionCode57other implements SolutionCode57Interface {

  public int[][] insert(int[][] intervals, int[] newInterval) {

    if (null == intervals
        || intervals.length <= 0 && (null == newInterval || newInterval.length <= 0)) {
      return intervals;
    }
    // 当原始数据不存在，只存在插入数据时，返回的操作
    if ((null == intervals || intervals.length <= 0) && newInterval != null) {
      int[][] outData = new int[1][2];
      outData[0] = newInterval;
      return outData;
    }

    int index = 0;
    int length = intervals.length;

    List<int[]> data = new ArrayList<>(intervals.length);

    boolean addFlag = false;

    while (index < length) {
      int left = intervals[index][0];
      int right = intervals[index][1];

      boolean scope = false;

      while (index <= length - 1
          && intervals[index][1] >= newInterval[0]
          && newInterval[1] >= intervals[index][0]) {
        left = Math.min(left, intervals[index][0]);
        left = Math.min(left, newInterval[0]);
        right = Math.max(intervals[index][1], newInterval[1]);
        index++;
        scope = true;
        // 用于标识当前新数据被加入到排序集合中的标识
        addFlag = true;
      }

      data.add(new int[] {left, right});

      if (!scope) {
        index++;
      }
    }

    // 如果未加入，则需要手动加入
    if (!addFlag) {
      data.add(newInterval);
    }

    int[][] rspData = data.toArray(new int[data.size()][2]);
    sort(rspData);
    return rspData;
  }

  /**
   * 使用冒泡排序 需要4ms
   *
   * @param rspData
   */
  private void sort(int[][] rspData) {
    int[] tmp;
    for (int i = 0; i < rspData.length; i++) {
      boolean brakFlag = false;
      for (int j = 0; j < rspData.length - i - 1; j++) {
        if (rspData[j][0] > rspData[j + 1][0]) {
          tmp = rspData[j + 1];
          rspData[j + 1] = rspData[j];
          rspData[j] = tmp;
        }
      }
      if (brakFlag) {
        break;
      }
    }
  }

  /**
   * 使用插入排序 需要4ms
   *
   * @param rspData
   */
  private void insertsort(int[][] rspData) {
    int[] insertData;
    int index = 0;
    for (int i = 1; i < rspData.length; i++) {
      index = i;
      insertData = rspData[i];
      // 查找插入点
      for (int j = 0; j < i; j++) {
        if (rspData[i][0] < rspData[j][0]) {
          index = j;
          break;
        }
      }

      // 进行数据的移动操作
      for (int j = i; j > index; j--) {
        rspData[j] = rspData[j - 1];
      }
      // 将插入点放入
      rspData[index] = insertData;
    }
  }
}
