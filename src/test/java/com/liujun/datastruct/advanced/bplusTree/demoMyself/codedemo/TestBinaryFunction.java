package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找算法
 *
 * @author liujun
 * @since 2022/11/8
 */
public class TestBinaryFunction {

  @Test
  public void success() {
    List<Integer> dataValue = new ArrayList<>();

    dataValue.add(1);
    dataValue.add(3);
    dataValue.add(5);
    dataValue.add(8);
    dataValue.add(10);

    Assertions.assertEquals(-2, BinaryFunction.indexedBinarySearch(dataValue, 2));
    Assertions.assertEquals(0, BinaryFunction.indexedBinarySearch(dataValue, 1));
    Assertions.assertEquals(4, BinaryFunction.indexedBinarySearch(dataValue, 10));
  }
}
