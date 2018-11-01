package com.liujun.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 使用基数排序
 *
 * <p>其思想源于将数据中的位切分成一个一个，然后使用计数排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/11/1
 */
public class RadixSort {

  public void radixSort(int[] data) {
    // 1,找出最大值
    int max = data[0];
    for (int i = 1; i < data.length; i++) {
      if (data[i] > max) {
        max = data[i];
      }
    }

    // 计算得出需要运行的位
    int bitNum = 1;
    int tmpRsp = max;
    while (max / 10 > 0) {
      max = max / 10;
      bitNum++;
    }
  }

  /**
   * 按每位进行计数排序操作
   *
   * @param data 数据，
   * @param divsor 需要排序的位
   */
  public void countSort(int[] data, int divsor) {
    // 默认即使用10个桶，即0-9
    int[] bucket = new int[10];

    // 对第桶内的数据进行计数
    for (int i = 0; i < data.length; i++) {
      int index = data[i] / divsor % 10;
      bucket[index]++;
    }

    System.out.println("统计结果:" + Arrays.toString(bucket));

    // 2,对桶内的数据进行累加
    for (int i = 1; i < bucket.length; i++) {
      bucket[i] = bucket[i - 1] + bucket[i];
    }

    System.out.println("统计求和结果:" + Arrays.toString(bucket));

    int[] sortData = new int[data.length];

    for (int i = data.length - 1; i >= 0; i--) {
      int buckIndex = data[i] / divsor % 10;
      System.out.println("桶位置:" + buckIndex);
      sortData[bucket[buckIndex] - 1] = data[i];
      bucket[buckIndex]--;
    }

    for (int i = 0; i < sortData.length; i++) {
      data[i] = sortData[i];
    }

    System.out.println("排序结果:" + Arrays.toString(sortData));
  }

  public static void main(String[] args) {
    int[] data = new int[] {10, 20, 30, 40, 55, 60, 14, 2, 123};
    // int[] data = new int[] {3, 500};

    RadixSort instance = new RadixSort();
    instance.radixSort(data);
    instance.countSort(data, 1);
    instance.countSort(data, 10);
    instance.countSort(data, 100);
  }
}
