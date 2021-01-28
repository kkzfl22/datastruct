package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试布隆过滤器操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBoomFilterOperator {

  @Test
  public void baseOperator() {
    BoomFilterOperator operator = new BoomFilterOperator();
    String data = "12312";
    operator.put(data);
    boolean exists = operator.mightContain(data);
    Assert.assertEquals(true, exists);

    boolean existsData = operator.mightContain(data + "sesd");
    Assert.assertEquals(false, existsData);
  }

  @Test
  public void baseOperatorMany() {
    BoomFilterOperator operator = new BoomFilterOperator();

    for (int i = 0; i < 2000000; i++) {
      String data = "i" + i;
      operator.put(data);
      boolean exists = operator.mightContain(data);
      Assert.assertEquals(true, exists);
    }
  }
}
