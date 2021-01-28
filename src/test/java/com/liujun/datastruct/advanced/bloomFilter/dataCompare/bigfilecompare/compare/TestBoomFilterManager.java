package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * 测试大型数据过滤器
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBoomFilterManager {

  @Test
  public void bigCheckTest() {
    long nums = 2000000;
    BoomFilterManager manager = new BoomFilterManager(nums);

    for (int i = 0; i < nums; i++) {
      String data = String.valueOf(i) + System.nanoTime();
      manager.put(data);
      boolean exists = manager.mightContain(data);
      Assert.assertEquals(true, exists);
    }

    for (long i = nums; i < nums + nums; i++) {
      String data = String.valueOf(i) + System.nanoTime();
      boolean exists = manager.mightContain(data);
      Assert.assertEquals(false, exists);
    }
  }

  @Test
  public void testFilter() {
    BoomFilterManager manager = new BoomFilterManager(1);

    int dataIndex = manager.getIndex(new File("d://name/test-12.bloom"));
    Assert.assertEquals(12, dataIndex);
  }
}
