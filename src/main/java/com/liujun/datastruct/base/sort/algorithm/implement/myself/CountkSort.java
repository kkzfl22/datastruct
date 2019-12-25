package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import java.util.Arrays;

/**
 * 计数排序
 *
 * <p>对年龄进行排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/11/26
 */
public class CountkSort {

  public void sort(int[] data) {

    // 进行最基本的数据判断操作
    if (data == null || data.length == 0) {
      return;
    }

    // 1,定义数据的范围
    int scope = 9;
    int[] scopeArray = new int[scope];

    // 遍历数据，将各年龄的数据进行统计
    for (int i = 0; i < data.length; i++) {
      scopeArray[data[i] - 1] += 1;
    }

    int sum = 0;
    // 进行顺序求和操作
    for (int i = 0; i < scopeArray.length; i++) {
      sum += scopeArray[i];
      scopeArray[i] = sum;
    }

    int[] outarray = new int[data.length];

    // 逆序遍历原始数据
    for (int i = data.length - 1; i >= 0; i--) {
      int outdataIndex = data[i];
      // 得到当前值在求和数组中的索引
      outdataIndex = outdataIndex - 1;
      // 计算目标输出位置
      int countData = scopeArray[outdataIndex];
      // 求得数据位置
      outarray[countData - 1] = data[i];
      // 进行计数结果的减1操作
      scopeArray[outdataIndex] = scopeArray[outdataIndex] - 1;
    }

    System.out.println(Arrays.toString(outarray));
  }
}
