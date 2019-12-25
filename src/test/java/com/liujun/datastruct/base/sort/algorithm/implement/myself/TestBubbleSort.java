package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;
import com.liujun.datastruct.base.sort.algorithm.TestSortBase;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/16
 */
public class TestBubbleSort extends TestSortBase {

  private SortInf<Integer> value = new BubbleSort<>();

  @Override
  public SortInf getInstance() {
    return value;
  }
}
