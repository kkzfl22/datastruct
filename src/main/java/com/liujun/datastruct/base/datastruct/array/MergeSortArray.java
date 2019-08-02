package com.liujun.datastruct.base.datastruct.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行两个有序数组的合并操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/29
 */
public class MergeSortArray {

  /** 循环遍历结束 */
  public static final int LOOP_FINISH_FLAG = -1;

  /** 数组索引标识 */
  public static final int ARRAY_ONE_FLAG = 0;

  /** 数组索引标识 */
  public static final int ARRAY_TWO_FLAG = 1;

  /**
   * 进行有序数组的合并操作
   *
   * @param dataArray 多个有序的数组
   * @return 合并之后的有序数组
   */
  public List<Integer> mergeSortArray(int[] dataArray, int[] sortData) {

    List<Integer> result = new ArrayList<>(dataArray.length + sortData.length);

    int[] dataIndex = new int[2];

    dataIndex[0] = 0;
    dataIndex[1] = 0;

    // 1,取得当前较小的的数组
    int minArray = -1;
    int dataValue = -1;
    while ((minArray = minute(dataArray, sortData, dataIndex)) != LOOP_FINISH_FLAG) {
      if (ARRAY_ONE_FLAG == minArray) {
        dataValue = dataArray[dataIndex[ARRAY_ONE_FLAG]];
        if (dataArray.length <= dataIndex[ARRAY_ONE_FLAG]) {
          break;
        }
        dataIndex[ARRAY_ONE_FLAG]++;

      } else if (ARRAY_TWO_FLAG == minArray) {
        dataValue = sortData[dataIndex[ARRAY_TWO_FLAG]];
        if (sortData.length <= dataIndex[ARRAY_TWO_FLAG]) {
          break;
        }
        dataIndex[ARRAY_TWO_FLAG]++;
      }

      result.add(dataValue);
    }

    // 将数组中空余数据写入到集合中
    if (dataArray.length > dataIndex[ARRAY_ONE_FLAG]) {
      for (int i = dataIndex[ARRAY_ONE_FLAG]; i < dataArray.length; i++) {
        result.add(dataArray[i]);
      }
    } else if (sortData.length > dataIndex[ARRAY_TWO_FLAG]) {
      for (int i = dataIndex[ARRAY_TWO_FLAG]; i < sortData.length; i++) {
        result.add(sortData[i]);
      }
    }

    return result;
  }

  private int minute(int[] dataArray, int[] sortData, int[] arrayData) {

    // 检查是否超范围
    if (arrayData[0] >= dataArray.length || arrayData[1] >= sortData.length) {
      return LOOP_FINISH_FLAG;
    }

    int index1 = dataArray[arrayData[0]];
    int index2 = sortData[arrayData[1]];

    if (index1 <= index2) {
      return ARRAY_ONE_FLAG;
    }
    return ARRAY_TWO_FLAG;
  }
}
