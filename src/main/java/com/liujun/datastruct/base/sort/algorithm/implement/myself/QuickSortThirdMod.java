package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

/**
 * 快速排序的代码实现,使用三数取中法进行分区点的选择操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/28
 */
public class QuickSortThirdMod<T> implements SortInf<T> {

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
    // 1,分区函数采用三数取中法
    int countPoint = this.dataMid(data, s, t);

    // 检查是否为最后一个数，非最后一个数，则直接互换操作
    if (countPoint != t) {
      // 求得中位数后将，最后一个位置与中位数互换即可
      swap(data, countPoint, t);
    }

    // 当前的分区点
    int midPoint = t;

    int i = s;
    for (int j = s; j <= t - 1; j++) {
      // i < j ? -1
      // 如果数据小于分区点，执行互换操作
      if (((Comparable) data[j]).compareTo(data[midPoint]) < 0) {
        // 交换数据
        swap(data, i, j);
        // i向后移动一位，指向分区点
        i += 1;
      }
      /// System.out.println("分区数据:" + Arrays.toString(data));
    }

    // 当结束后，将分区点的位置与i互换，即可完成
    swap(data, i, midPoint);

    //    System.out.println("分区点:" + midPoint);
    //    System.out.println("分区完成后" + Arrays.toString(data));

    return i;
  }

  /**
   * 三数取中法
   *
   * <p>对三个数进行比较，然后交换次序
   *
   * @param data 数据
   * @param s 开始索引
   * @param t 结束索引
   * @return 当前的中位数
   */
  public int dataMid(T[] data, int s, int t) {
    // 求两个数的中位数与(s+t)/2效果一致，但在接近最大数时，将不能运算，所以使用t+((s-t)>>1)
    int midIndex = t + ((s - t) >> 1);

    // 首先保证前两个数是有序的
    if (((Comparable) data[s]).compareTo(data[midIndex]) > 0) {
      swap(data, s, midIndex);
    }

    // 分为多种情况，情况1，比最小的数字小
    if (((Comparable) data[t]).compareTo(data[s]) <= 0) {
      return s;
    }
    // 情况2，大于最小，小于最大
    else if (((Comparable) data[t]).compareTo(data[s]) > 0
        && ((Comparable) data[t]).compareTo(data[midIndex]) <= 0) {
      return t;
    }
    // 情况3：大于最大的数，返回最大数
    else {
      return midIndex;
    }
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
