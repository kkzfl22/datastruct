package com.liujun.datastruct.algorithm.backtrackingAlgorithm.eightQueens;

import org.junit.Test;

/**
 * 进行八皇后问题的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/24
 */
public class TestMyEightQueen {

  @Test
  public void testEightQueen() {
    MyEightQueen instance = new MyEightQueen();
    instance.call8Queens(0);
  }
}
