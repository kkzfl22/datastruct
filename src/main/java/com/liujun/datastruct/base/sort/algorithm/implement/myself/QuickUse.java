package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import java.util.Arrays;

/**
 * 在O(n) 时间复杂度内求无序数组中的第 K 大元素。比如，4， 2， 5， 12， 3
 *
 * <p>此总是的求解可使用快速排序的分区函数的思想
 *
 * <p>任意选择一点分区点进行数据分区操作
 *
 * <p>当分区的索引位置在小于k时，就接着在左分区中继续递归。
 *
 * <p>当分区的索引位置在大于k时，就在右分区分区中继续递归。
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/30
 */
public class QuickUse {

  public int quickFinkIndex(int[] data, int k) {
    return this.recursion(data, 0, data.length - 1, k);
  }

  private int recursion(int[] data, int start, int end, int k) {

    // 1,进行分区函数操作
    int point = this.parttition(data, start, end);
    if (point > k) {
      return recursion(data, start, point - 1, k);
    } else if (point < k) {
      return recursion(data, point + 1, end, k);
    }

    return data[point];
  }

  private int parttition(int[] data, int start, int end) {
    // 分区点
    int point = end;
    // 开始位置
    int startLeft = start;
    int startRitht;
    for (startRitht = start; startRitht <= end; startRitht++) {
      // 如果当前数小于分区点
      if (data[startRitht] < data[point]) {
        // 进行数据交换操作
        int tmp = data[startRitht];
        data[startRitht] = data[startLeft];
        data[startLeft] = tmp;
        startLeft += 1;
      }
    }
    // 最后将分区点放入
    int datatmp = data[point];
    data[point] = data[startLeft];
    data[startLeft] = datatmp;

    return startLeft;
  }


}
