package com.liujun.datastruct.datacompare.bigfilecompare.updfile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试快速排序
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestQuickSort {

  @Test
  public void quickSort() {

    List<Integer> data = new ArrayList<>(10);
    data.add(8);
    data.add(5);
    data.add(7);
    data.add(3);
    data.add(2);
    data.add(20);

    QuickSort.quickSortList(data);

    for (Integer item : data) {
      System.out.println(item);
    }
  }
}
