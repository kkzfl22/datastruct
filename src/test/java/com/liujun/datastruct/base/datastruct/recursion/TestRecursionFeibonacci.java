package com.liujun.datastruct.base.datastruct.recursion;

import org.junit.Assert;
import org.junit.Test;

/**
 * 斐波那契数的求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class TestRecursionFeibonacci {

  @Test
  public void testfeibonacci() {

    RecursionFibonacci instance = new RecursionFibonacci();

    Assert.assertEquals(2, instance.fibonacci(3));
    Assert.assertEquals(3, instance.fibonacci(4));
    Assert.assertEquals(8, instance.fibonacci(6));
    Assert.assertEquals(55, instance.fibonacci(10));
    Assert.assertEquals(6765, instance.fibonacci(20));
  }

  /** 测试栈溢出操作 */
  @Test
  public void testfeibonacciStackOverFlow() {

    RecursionFibonacci instance = new RecursionFibonacci();

    instance.fibonacci(100000);
  }

  /** 限制最大递归深度的 */
  @Test(expected = IllegalArgumentException.class)
  public void testfeibonacciSetMax() {

    RecursionFibonacci instance = new RecursionFibonacci();

    instance.fibonacciSetDept(100000);
  }

  @Test
  public void testfeibonacciAddCache() {
    RecursionFibonacci instance = new RecursionFibonacci();
    int value = instance.fibonacciAddCache(5000);
    System.out.println(value);
  }

  @Test
  public void testfeibonacciLoop()
  {
    RecursionFibonacci instance = new RecursionFibonacci();
    Assert.assertEquals(2, instance.fibonacciLoop(3));
    Assert.assertEquals(3, instance.fibonacciLoop(4));
    Assert.assertEquals(5, instance.fibonacciLoop(5));
    Assert.assertEquals(8, instance.fibonacciLoop(6));
    Assert.assertEquals(55, instance.fibonacciLoop(10));
    Assert.assertEquals(6765, instance.fibonacciLoop(20));
  }
}
