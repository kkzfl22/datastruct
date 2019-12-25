package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 快速排序的代码实现
 *
 * <p>使用随机分区点的方法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/28
 */
public class QuickSortRandomPoint<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {

    if (data == null || data.length <= 1) {
      return;
    }
    if (!(data[0] instanceof Comparable)) {
      throw new IllegalArgumentException("data not implement compator interface");
    }

    // 调用快速排序
    this.quickSort(data, 0, data.length - 1);
  }

  private void quickSort(T[] data, int s, int t) {

    // 当前需操作元素仅一个，则退出
    if (t - s < 1) {
      return;
    }

    int point = this.partition(data, s, t);
    quickSort(data, s, point - 1);
    quickSort(data, point + 1, t);
  }

  /**
   * 分区函数，在快速排序中使用的是原地分区，实现非常的巧妙
   *
   * <p>函数需要返回分区点
   *
   * @param data 原始数据
   * @param s 开始位置
   * @param t 结束位置
   * @return 分区点的位置
   */
  private int partition(T[] data, int s, int t) {
    // 1,采用随机的方法采用分区点，进行分区
    int countPoint = ThreadLocalRandom.current().nextInt(s, t);
    // 求得中位数后将，最后一个位置与中位数互换即可
    swap(data, countPoint, t);

    // 当前的分区点
    int midPoint = t;

    int i = s;
    for (int j = s; j <= t; j++) {
      // i < j ? -1
      // 如果数据小于分区点，执行互换操作
      if (((Comparable) data[j]).compareTo(data[midPoint]) < 0) {
        // 交换数据
        swap(data, i, j);
        // i向后移动一位，指向分区点
        i += 1;
      }
    }

    // 当结束后，将分区点的位置与i互换，即可完成
    swap(data, i, midPoint);

    return i;
  }

  /**
   * 进行数据交换操作
   *
   * @param data 原始数据
   * @param src 原始位置
   * @param target 目标位置
   */
  private void swap(T[] data, int src, int target) {
    T dataTmp = data[src];
    data[src] = data[target];
    data[target] = dataTmp;
  }
}
