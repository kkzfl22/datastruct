package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/22
 */
public class CodeTest {

  @Test
  public void testbinArrySeach() {
    List<Integer> value = new LinkedList<>();

    value.add(1);
    value.add(3);
    value.add(5);
    value.add(8);

    int index = Collections.binarySearch(value, 4);
    System.out.println(index);
    System.out.println("插入位置:" + (-index - 1));

    int index2 = Collections.binarySearch(value, 3);
    System.out.println(index2);
    System.out.println("插入位置2:" + (-index2 - 1));
    System.out.println("--------------------------");

    int indexZone = Collections.binarySearch(value, 0);
    System.out.println("二分查找返回位置:" + indexZone);
    System.out.println("插入位置:" + (-indexZone - 1));
    value.add(-indexZone - 1, 0);
    System.out.println(value);
  }
}
