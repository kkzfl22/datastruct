package com.liujun.math.chapter04Conclude;

import org.junit.Test;

/**
 * 进行比对
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestWheatCount {

  @Test
  public void testWheatCount() {
    WheatCount instance = new WheatCount();
    instance.wheatCountIterate();
    instance.wheatCountConclude();
  }
}
