package com.liujun.datastruct.datacompare.bigfilecompare.compare;

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

  @Test
  public void testFilterManager() {

    BoomFilterManager manager =
        new BoomFilterManager("D:\\run\\compare\\miffile\\output\\bloomfilter\\bloom_src");

    BoomFilterManager targetManager =
        new BoomFilterManager("D:\\run\\compare\\miffile\\output\\bloomfilter\\bloom_target");

    System.out.println("src:" + manager.mightContain("4742"));
    System.out.println("target:" + targetManager.mightContain("4742"));
  }
}
