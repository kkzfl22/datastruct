package com.liujun.sort;

import java.util.Arrays;

/**
 * 计数排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class CountSort {

  /**
   * 使用计数排序
   *
   * @param data
   */
  public int[] countSort(int[] data) {

    int dataLength = data.length;

    int maxScore = 0;

    // 1,找到最大分数
    for (int i = 0; i < dataLength; i++) {
      if (data[i] > maxScore) {
        maxScore = data[i];
      }
    }

    // 2,声明一个分数桶，统计各分数
    int[] scoreDataArray = new int[maxScore + 1];

    for (int i = 0; i < dataLength; i++) {
      scoreDataArray[data[i]]++;
    }
    System.out.println("分数分桶计数:" + Arrays.toString(scoreDataArray));

    // 3，按桶的顺序进行求和操作
    int[] scoreDataArraySum = new int[maxScore + 1];

    scoreDataArraySum[0] = scoreDataArray[0];

    for (int i = 1; i <= maxScore; i++) {
      scoreDataArraySum[i] = scoreDataArray[i] + scoreDataArraySum[i - 1];
    }
    System.out.println("顺序求和：" + Arrays.toString(scoreDataArraySum));

    // 进行排序的操作
    int[] arraySort = new int[dataLength];

    for (int i = dataLength - 1; i >= 0; i--) {
      int dataIndex = scoreDataArraySum[data[i]];
      arraySort[dataIndex - 1] = data[i];
      scoreDataArraySum[data[i]]--;
    }

    return arraySort;
  }
}
