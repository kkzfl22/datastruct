package com.liujun.datastruct.base.search.binarysearch2.leetcode744;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试查找最小字母
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSolution() {
      this.assertTarget(new char[]{'c', 'f', 'j'},'a','c');
      this.assertTarget(new char[]{'c', 'f', 'j'},'c','f');
      this.assertTarget(new char[]{'c', 'f', 'j'},'d','f');
      this.assertTarget(new char[]{'c', 'f', 'j'},'g','j');
      this.assertTarget(new char[]{'c', 'f', 'j'},'j','c');
      this.assertTarget(new char[]{'c', 'f', 'j'},'k','c');

  }

  /**
   * 对比目标值
   *
   * @param letters
   * @param target
   * @param resultCompare
   */
  private void assertTarget(char[] letters, char target, char resultCompare) {
    char targetRsp = instance.nextGreatestLetter(letters, target);
    Assert.assertEquals(targetRsp, resultCompare);
  }
}
