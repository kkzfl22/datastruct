package com.liujun.datastruct.base.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * <p>最好的情况，数据已经是有序的，只需一次遍历，不用交换，即为O(n)
 *
 * <p>最坏情况，数据是倒序的，需要进行N次的数据交换，即为O(n的平方)
 *
 * <p>有序度=N*(N-1)/2,N为元素的个数
 *
 * <p>有序数=数据中有序数数的个数，即为两个数为序的个数，可不连续
 *
 * <p>平均复杂度,使用逆序度=满有序度-有序度，平均情下为N*(N-1)/4,上限为O(n的平方），平时时间复杂度为O(N的平方）
 *
 * <p>冒泡排序为原地排序算法，不需要消消耗额外的存储空间
 *
 * <p>冒泡排序是稳定的排序算法，在完成排序后，相同的数据，不会被交换位置
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/28
 */
public class BubbleSort {

  public void bobbleSort(int[] n) {
    int length = n.length;

    if (length < 1) {
      return;
    }

    int bubbleNum = 0;

    int tmpValue = 0;

    for (int i = 0; i < length; i++) {

      for (int j = 0; j < length; j++) {

        if (n[i] > n[j]) {
          tmpValue = n[i];
          n[i] = n[j];
          n[j] = tmpValue;
          bubbleNum++;
        }
      }
    }
    System.out.println("数组大小:" + n.length);
    System.out.println(Arrays.toString(n));
    System.out.println("排序次数:" + bubbleNum);
  }

  public void bobbleSortOptimizing(int[] n) {
    int length = n.length;

    if (length < 1) {
      return;
    }

    int bubbleNum = 0;

    int tmpValue = 0;

    for (int i = 0; i < length; i++) {

      for (int j = 0; j < length; j++) {

        if (n[i] > n[j]) {
          tmpValue = n[i];
          n[i] = n[j];
          n[j] = tmpValue;
          bubbleNum++;
          break;
        }
      }
    }
    System.out.println("数组大小:" + n.length);
    System.out.println(Arrays.toString(n));
    System.out.println("排序次数:" + bubbleNum);
  }
}
