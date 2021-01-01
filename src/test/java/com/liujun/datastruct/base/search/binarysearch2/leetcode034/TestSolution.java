package com.liujun.datastruct.base.search.binarysearch2.leetcode034;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试查找开始与结束位置
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private Solution instance = new Solution();

  @Test
  public void testSearchRang() {
    this.searchRangeInvoke(new int[] {5, 7, 7, 8, 8, 10}, 8, new int[] {3, 4});
    this.searchRangeInvoke(new int[] {5, 7, 7, 8, 8, 10}, 6, new int[] {-1, -1});
    this.searchRangeInvoke(new int[] {}, 0, new int[] {-1, -1});
    this.searchRangeInvoke(new int[] {1}, 1, new int[] {0, 0});
    this.searchRangeInvoke(new int[] {2, 2}, 2, new int[] {0, 1});
    this.searchRangeInvoke(new int[] {3, 3, 3}, 3, new int[] {0, 2});
    this.searchRangeInvoke(new int[] {3, 3, 3, 3, 3}, 3, new int[] {0, 4});
  }

  private void searchRangeInvoke(int[] nums, int target, int[] tars) {
    int[] numsTarget = instance.searchRange(nums, target);
    this.arrayCompare(numsTarget, tars);
  }

  /**
   * 数组的断言
   *
   * @param src 原
   * @param target 目标
   */
  private void arrayCompare(int[] src, int[] target) {
    boolean compareFlag;

    for (int i = 0; i < src.length; i++) {
      compareFlag = false;

      for (int j = 0; j < target.length; j++) {
        if (src[i] == target[j]) {
          compareFlag = true;
          break;
        }
      }

      if (!compareFlag) {
        Assert.fail(src[i] + " not target");
      }
    }
    Assert.assertTrue(true);
  }
}
