package com.liujun.datastruct.array;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/18
 */
public class TestMyArray {

  @Test
  public void testInsert() {
    MyArray array = new MyArray(5);
    array.addvalue(1);
    array.addvalue(2);
    array.addvalue(3);
    array.addvalue(4);
    array.addvalue(5);
    System.out.println(Arrays.toString(array.getArray()));
    array.insert(4, 7);
    System.out.println(Arrays.toString(array.getArray()));
    array.insert(5, 9);
    System.out.println(Arrays.toString(array.getArray()));
  }
}
