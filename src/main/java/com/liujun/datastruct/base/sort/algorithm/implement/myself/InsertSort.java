package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.util.Arrays;

/**
 * 插入排序的代码实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/20
 */
public class InsertSort<T> implements SortInf<T> {

  @Override
  public void sort(T[] data) {
    // 1,进行数据的检查
    if (null == data || data.length <= 1) {
      return;
    }
    // 进行数据的检查
    if (!(data[0] instanceof Comparable)) {
      throw new IllegalArgumentException("data not implement compator interface");
    }

    int dataLen = data.length;
    T insertData;

    for (int i = 1; i <= dataLen - 1; i++) {

      int insertPos = i;
      insertData = data[i];

      // 1,查找插入位置
      for (int j = 0; j < i; j++) {
        // 按从小到大,x < j ? -1
        if (((Comparable) data[j]).compareTo(insertData) > 0) {
          insertPos = j;
          break;
        }
      }
      // 将数据依次向后移动一位
      for (int j = i; j > insertPos; j--) {
        data[j] = data[j - 1];
      }

      // 将数据放入
      data[insertPos] = insertData;

      System.out.println("当前前第:" + i + "次：" + Arrays.toString(data));
    }
  }
}
