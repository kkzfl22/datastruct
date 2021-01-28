package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.updfile;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 使用快速排序对集合进行排序操作
 *
 * <p>采用随机法来进行分区点的确定，让性能不至于极端的退化
 *
 * @author liujun
 * @version 0.0.1
 */
public class QuickSort {

  private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

  /**
   * 快速排序的算法实现
   *
   * @param data
   */
  public static <V> void quickSortList(List<V> data) {
    if (data == null || data.isEmpty()) {
      return;
    }
    // 是否实现的比较函数的检查
    if (!(data.get(0) instanceof Comparable)) {
      throw new IllegalArgumentException("this data not implement Comparable interface! ");
    }

    quickSort(data, 0, data.size() - 1);
  }

  private static <V> void quickSort(List<V> data, int s, int t) {
    // 当前仅排序的函数一个，则退出
    if (t - s < 1) {
      return;
    }
    // 分区点
    int partition = partition(data, s, t);
    // 左边继续分工区操作
    quickSort(data, s, partition - 1);
    quickSort(data, partition + 1, t);
  }

  /**
   * 快速排序，每次可以让一个数正确的得到在数据中的位置
   *
   * @param data
   * @param s
   * @param t
   * @return
   */
  private static <V> int partition(List<V> data, int s, int t) {
    // 分区点,采用随机法
    int randPoint = RAND.nextInt(s, t);
    // 然后将数据与末尾互换，
    swap(data, t, randPoint);

    int point = t;
    int i = s;
    for (int j = s; j <= t - 1; j++) {
      if (((Comparable) data.get(j)).compareTo(data.get(point)) == -1) {
        // 让i和j的位置互换
        swap(data, i, j);
        i += 1;
      }
    }

    // 遍历结束后，让i与分区点交换
    swap(data, i, point);
    // 返回分区点的位置
    return i;
  }

  private static <V> void swap(List<V> data, int s, int t) {
    V srcItem = data.get(s);
    V targetItem = data.get(t);
    data.set(s, targetItem);
    data.set(t, srcItem);
  }
}
