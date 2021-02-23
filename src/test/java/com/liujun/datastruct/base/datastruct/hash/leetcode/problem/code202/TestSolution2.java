package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code202;

import org.junit.Assert;
import org.junit.Test;

/**
 * 判断是否为快乐数
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution2 {

  @Test
  public void isHappy() {
    Solution2 instance = new Solution2();
    Assert.assertEquals(true, instance.isHappy(19));
    Assert.assertEquals(false, instance.isHappy(2));
  }
}
