package com.liujun.datastruct.base.sort.leetcode.code56.implement;

import java.util.ArrayList;
import java.util.Arrays;
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
public class SolutionCode56 {

  public int[][] merge(int[][] intervals) {

    if (intervals == null || intervals.length <= 1) {
      return intervals;
    }

    // 1,按首个数字进行排序操作
    Arrays.sort(intervals, (o1, o2) -> (o1[0] - o2[0]));

    List<int[]> data = new ArrayList<>(intervals.length);
    data.add(intervals[0]);

    for (int i = 1; i < intervals.length; i++) {
      boolean addFlag = true;
      // 已排序的区间的查找操作
      for (int[] item : data) {
        // 1,检查开始值是否在已经区间中
        if (item[0] <= intervals[i][0] && intervals[i][0] <= item[1]) {
          addFlag = false;

          // 如果大于则将结束值替换为当前
          if (intervals[i][1] > item[1]) {
            item[1] = intervals[i][1];
          }
          break;
        }
      }
      if (addFlag) {
        data.add(intervals[i]);
      }
    }

    int[][] rsdata = new int[data.size()][2];

    for (int i = 0; i < data.size(); i++) {
      rsdata[i] = data.get(i);
    }

    return rsdata;
  }
}
