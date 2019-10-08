package com.liujun.datastruct.base.datastruct.recursion.leetcode.code51.implement;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.List;

/**
 * N皇后问题求解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/29
 */
public class NqueueSolutionTest {

  @Test
  public void testNqueue() {
    NQueuesSolution instance = new NQueuesSolution();
    List<List<String>> list = instance.solveNQueens(4);

    for (List<String> dataItem : list) {
      System.out.println(dataItem);
    }
  }
}
