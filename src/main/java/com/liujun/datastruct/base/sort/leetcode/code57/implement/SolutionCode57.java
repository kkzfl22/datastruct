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
public class SolutionCode57 implements SolutionCode57Interface {

  public int[][] insert(int[][] intervals, int[] newInterval) {

    if (null == intervals
        || intervals.length <= 0 && (null == newInterval || newInterval.length <= 0)) {
      return intervals;
    }

    // 首先将数据加入到数组中，然后排序
    int[][] intervalsNew = new int[intervals.length + 1][2];

    for (int i = 0; i < intervals.length; i++) {
      for (int j = 0; j < intervals[i].length; j++) {
        intervalsNew[i][j] = intervals[i][j];
      }
    }
    intervalsNew[intervals.length] = newInterval;

    // 对数据进行排序操作
    Arrays.sort(
        intervalsNew,
        new Comparator<int[]>() {
          @Override
          public int compare(int[] o1, int[] o2) {
            return o1[0] > o2[0] ? 1 : o1[0] < o2[0] ? -1 : 0;
          }
        });

    int index = 0;
    int length = intervalsNew.length;

    List<int[]> data = new ArrayList<>(intervalsNew.length);

    while (index < length) {
      int left = intervalsNew[index][0];
      int right = intervalsNew[index][1];
      while (index < length - 1 && (right >= intervalsNew[index + 1][0])) {
        right = Math.max(right, intervalsNew[index + 1][1]);
        index++;
      }
      data.add(new int[] {left, right});
      index++;
    }

    return data.toArray(new int[data.size()][2]);
  }
}
