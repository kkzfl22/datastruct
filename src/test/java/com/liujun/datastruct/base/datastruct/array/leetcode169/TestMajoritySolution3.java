package com.liujun.datastruct.base.datastruct.array.leetcode169;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/26
 */
public class TestMajoritySolution3 {

  @Test
  public void testMajority() {
    int[] value = new int[] {3, 2, 3};

    MajoritySolution3 test = new MajoritySolution3();
    int outvalue = test.majorityElement(value);
    Assert.assertEquals(outvalue, 3);
  }

  @Test
  public void testMajorityMore() {
    int[] data = new int[] {2, 2, 1, 1, 1, 2, 2};
    MajoritySolution3 majorityInstance = new MajoritySolution3();
    int majorityOut = majorityInstance.majorityElement(data);
    Assert.assertEquals(majorityOut, 2);
  }


  @Test
  public void testMajorityMoreValue() {
    int[] data = new int[] {3,2,3,2,3,2,2};
    MajoritySolution3 majorityInstance = new MajoritySolution3();
    int majorityOut = majorityInstance.majorityElement(data);
    Assert.assertEquals(majorityOut, 2);
  }

}
