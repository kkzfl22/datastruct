package com.liujun.datastruct.base.datastruct.recursion;

import org.junit.Assert;
import org.junit.Test;

/**
 * 求阶乘
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class TestRecurstionFactorial {

  @Test
  public void testFactorial() {

    RecurstionFactorial instance = new RecurstionFactorial();
    Assert.assertEquals(1, instance.factorial(1));
    Assert.assertEquals(2, instance.factorial(2));
    Assert.assertEquals(6, instance.factorial(3));
    Assert.assertEquals(24, instance.factorial(4));
    Assert.assertEquals(120, instance.factorial(5));
  }

  @Test
  public void testCountMax() {
    RecurstionFactorial instance = new RecurstionFactorial();
    instance.factorial(100000);
  }


  @Test
  public void testFactorialLoop() {

    RecurstionFactorial instance = new RecurstionFactorial();
    Assert.assertEquals(1, instance.factorialLoop(1));
    Assert.assertEquals(2, instance.factorialLoop(2));
    Assert.assertEquals(6, instance.factorialLoop(3));
    Assert.assertEquals(24, instance.factorialLoop(4));
    Assert.assertEquals(120, instance.factorialLoop(5));
  }

}
