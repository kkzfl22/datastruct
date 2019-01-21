package com.liujun.datastruct.base.datastruct.queue.leetcode.code703;

import com.liujun.datastruct.utils.RandomUtils;
import org.junit.Test;

/**
 * 返回数据流中第K大元素
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class TestKthLargest {

  @Test
  public void testAdd() {
    int[] arrs = new int[] {1, 2, 4, 2, 5, 7, 2, 4, 10, 289};

    KthLargest large = new KthLargest(6, arrs);

    for (int i = 0; i < 200; i++) {
      System.out.print(large.add(RandomUtils.getNextInt(999999)) + "\t");
      //  System.out.print(large.add(i) + "\t");
      if (i % 10 == 0) {
        System.out.println();
      }
    }
  }
}
