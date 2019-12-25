package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/30
 */
public class TestQuickUse {

  @Test
  public void testQuickUse() {
    QuickUse use = new QuickUse();

    int[] testData = new int[] {5, 3, 8, 2, 7, 6};
    Arrays.sort(testData);

    for (int i = 0; i < testData.length; i++) {
      int[] srcTmp = new int[] {5, 3, 8, 2, 7, 6};
      int value = use.quickFinkIndex(srcTmp, i);

      Assert.assertEquals(value, testData[i]);
    }
  }

  @Test
  public void testQuickUse2() {
    QuickUse use = new QuickUse();

    int[] testData = new int[] {6, 1, 3, 5, 7, 2, 4, 9, 11, 8};
    Arrays.sort(testData);

    for (int i = 0; i < testData.length; i++) {
      int[] srcTmp = new int[] {6, 1, 3, 5, 7, 2, 4, 9, 11, 8};
      int value = use.quickFinkIndex(srcTmp, i);

      Assert.assertEquals(value, testData[i]);
    }
  }
}
