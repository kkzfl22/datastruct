package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试一致性hash算法
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestConsistentHashDefault {

  @Test
  public void testDataPut() {
    ConsistentHashDefault dataHash = new ConsistentHashDefault();

    dataHash.addNode("192.168.1.1");
    dataHash.addNode("192.168.1.2");
    dataHash.addNode("192.168.1.3");
    dataHash.addNode("192.168.1.5");

    dataHash.defaultNode("192.168.1.3");

    for (int i = 0; i < 100; i++) {
      String node = dataHash.getNode(RandomStringUtils.randomAlphabetic(10));
      System.out.println(node);
      Assert.assertNotNull(node);
    }

    System.out.println("----------------");

    dataHash.dataDown("192.168.1.3");
    System.out.println("节点下线");
    for (int i = 0; i < 100; i++) {
      String node = dataHash.getNode(RandomStringUtils.randomAlphabetic(10));
      System.out.println(node);
      Assert.assertNotEquals(node, "192.168.1.3");
    }
  }
}
