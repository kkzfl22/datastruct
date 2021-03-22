package com.liujun.datastruct.base.leetcode.slide.code1004;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试最大连续1的个数
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  /** */
  @Test
  public void testLongestOnes1() {
    this.assertCheck(new int[] {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2, 6);
    this.assertCheck(new int[] {0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3, 10);
  }

  private void assertCheck(int[] src, int k, int target) {
    Solution solution = new Solution();
    this.assertCheck(solution,src,k,target);

    Solution2 solution2 = new Solution2();
    this.assertCheck2(solution2,src,k,target);

  }


  private void assertCheck(Solution solution,int[] src, int k, int target)
  {
    int result = solution.longestOnes(src, k);
    Assert.assertEquals(result, target);
  }

  private void assertCheck2(Solution2 solution,int[] src, int k, int target)
  {
    int result = solution.longestOnes(src, k);
    Assert.assertEquals(result, target);
  }


}
