package com.liujun.datastruct.base.datastruct.recursion;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试递归求解当前第几代的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class TestRecurstionGeneration {

  @Test
  public void testCountGeneration() {
    int n = 3;
    RecursionGeneration instance = new RecursionGeneration();
    int out = instance.countGeneration(n);

    System.out.println(out);
    Assert.assertEquals(10, out);
  }

  @Test
  public void testCountGenerationLoop() {
    RecursionGeneration instance = new RecursionGeneration();
    int rsp = instance.countGenerationLoop(3);
    Assert.assertEquals(10, rsp);
  }
}
