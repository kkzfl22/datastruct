package com.liujun.datastruct.datacompare.bigfilecompare.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试数据统计
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestDataStatistics {

  @Test
  public void testStatistics() {
    DataStatistics statistics = new DataStatistics(10000);

    for (int i = 0; i < 109090; i++) {
      statistics.dataAdd(1);
    }

    Assert.assertEquals(109090, statistics.getDataValue());
  }




}
