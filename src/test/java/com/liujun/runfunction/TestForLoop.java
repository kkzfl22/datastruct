package com.liujun.runfunction;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 进行循环测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/26
 */
public class TestForLoop {

  public static final int RUN_LOOP_NUM = 3000000;

  public static final List<Integer> list = new ArrayList<>(RUN_LOOP_NUM);

  @BeforeClass
  public static void before() {
    for (int i = 0; i < RUN_LOOP_NUM; i++) {
      list.add(i);
    }
  }

  @Test
  public void testRunForeachLoop() {
    for (Integer value : list) {
      if (value > RUN_LOOP_NUM) {
        System.out.println("...");
      }
    }
  }

  @Test
  public void testRunForLoopStaticValue() {
    Integer value;
    for (int i = 0; i < RUN_LOOP_NUM; i++) {
      value = list.get(i);
      if (value > RUN_LOOP_NUM) {
        System.out.println("...");
      }
    }
  }

  @Test
  public void testRunWhileLoop() {
    Integer value;
    int index = 0;

    while (index < RUN_LOOP_NUM) {
      value = list.get(index);
      if (value > RUN_LOOP_NUM) {
        System.out.println("...");
      }

      index++;
    }
  }

  @Test
  public void testStreamLoop() {
    list.stream()
        .forEach(
            value -> {
              if (value > RUN_LOOP_NUM) {
                System.out.println("...");
              }
            });
  }
}
