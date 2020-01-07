package com.liujun.datastruct.base.sort.leetcode.code56.implement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * merge intervals
 *
 * <p>合并区间
 *
 * <p>使用插入排序的思想来进行解决此合并区间的问题
 *
 * <p>1，将区间分为已合并区间与未合并区间
 *
 * <p>2，每次从未合并区间中拿出一个数，检查是否在已经合并区间中，如果在，则进行合并操作，不在区间则直接添加
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/06
 */
public class SolutionCode562 {

  public int[][] merge(int[][] intervals) {

    if (intervals == null || intervals.length <= 1) {
      return intervals;
    }

    // 1，按首个数字进行排序操作
    // Arrays.sort(intervals, (o1, o2) -> (o1[0] > o2[0] ? 1 : o1[0] < o2[0] ? -1 : 0));
    Arrays.sort(
        intervals,
        new Comparator<int[]>() {
          @Override
          public int compare(int[] o1, int[] o2) {
            return o1[0] > o2[0] ? 1 : o1[0] < o2[0] ? -1 : 0;
          }
        });

    // 进行合并区间操作
    List<int[]> data = new ArrayList<>(intervals.length);

    int index = 0;
    int length = intervals.length;

    while (index < length) {
      int left = intervals[index][0];
      int right = intervals[index][1];
      // 当前数必须大于左区间最小数，
      while (index < length - 1 && right >= intervals[index + 1][0]) {
        right = Math.max(right, intervals[index + 1][1]);
        // 每合并一次向前进一次
        index++;
      }
      // 每放入一次，则向前进一次
      data.add(new int[] {left, right});
      index++;
    }

    return data.toArray(new int[data.size()][2]);
  }
}
