package com.liujun.datastruct.algorithm.dynamicProgramming.triangle;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/28
 */
public class TestTriangle {

  @Test
  public void triangle() {
    int[][] value = {
      {0, 0, 0, 0, 5},
      {0, 0, 0, 7, 0, 8},
      {0, 0, 2, 0, 3, 0, 4},
      {0, 4, 0, 9, 0, 6, 0, 1},
      {2, 0, 7, 0, 9, 0, 4, 0, 5}
    };


    Triangle instance = new Triangle();
    instance.count(value);
  }


  @Test
  public void triangle2() {
    int[][] value = {
            {0, 0, 0, 0, 5},
            {0, 0, 0, 7, 0, 8},
            {0, 0, 2, 0, 1, 0, 4},
            {0, 4, 0, 9, 0, 1, 0, 1},
            {2, 0, 7, 0, 9, 0, 1, 0, 5}
    };


    Triangle instance = new Triangle();
    instance.count(value);
  }
}
