package com.liujun.datastruct.base.sort.algorithm.implement.standard;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.util.Arrays;

/**
 * 标准的冒泡排序算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/19
 */
public class BubbleSort<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {
    if (null == data || data.length <= 1) {
      return;
    }

    int dataLength = data.length;

    for (int i = 0; i < dataLength - 1; i++) {
      boolean brakFlag = false;
      // 一次冒泡操作，至少一个元素放置到了排序之后所在的位置，比较交换的次数也会越来越少
      for (int j = 0; j < dataLength - i - 1; j++) {
        // 过行数据中的两两比较,(x < y) ? -1 : ((x == y) ? 0 : 1);
        // 在数值的比较中，如果左边的数比右边的数小，则返回-1，
        if (((Comparable) data[j]).compareTo(data[j + 1]) > 0) {
          T dataTmp = data[j];
          data[j] = data[j + 1];
          data[j + 1] = dataTmp;
          brakFlag = true;
        }
      }
      System.out.println("当前第:" + i + "次：" + Arrays.toString(data));
      // 如果一次交换则不存在数据交换时，则说明数据已经冒泡完成，无需再冒泡操作
      if (!brakFlag) {
        break;
      }
    }
  }
}
