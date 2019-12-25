package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/11/26
 */
public class BluckSort {

  public void sort(Integer[] data) {

    // 1k进行分桶操作
    List<List<Integer>> bluckList = bluckOper(data);

    // 进行桶数据的排序操作
    this.bluckSort(bluckList);
  }

  /**
   * 进行桶的排序操作
   *
   * @param bluckList
   * @return
   */
  private void bluckSort(List<List<Integer>> bluckList) {
    for (List<Integer> data : bluckList) {
      Collections.sort(data);
    }
  }

  /**
   * 进行数据的分桶操作
   *
   * @param data 数据信息
   * @return 分桶后的数据信息
   */
  private List<List<Integer>> bluckOper(Integer[] data) {
    List<List<Integer>> resultData = new ArrayList<>();

    // 1,初始化桶信息
    int indexTotal = 10;
    for (int i = 0; i < indexTotal; i++) {
      List<Integer> dataItem = new ArrayList<>();
      resultData.add(dataItem);
    }

    // 将当前数据放入放入到桶中
    for (Integer valuemsg : data) {
      Integer intvalue = valuemsg;
      int outIndex = intvalue % indexTotal;
      resultData.get(outIndex).add(valuemsg);
    }

    return resultData;
  }
}
