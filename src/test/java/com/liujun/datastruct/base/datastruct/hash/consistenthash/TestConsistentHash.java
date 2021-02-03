package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试一致性hash算法
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestConsistentHash {

  @Test
  public void testDataPut() {
    ConsistentHash dataHash = new ConsistentHash();

    dataHash.addNode("192.168.1.1");
    dataHash.addNode("192.168.1.2");
    dataHash.addNode("192.168.1.3");
    dataHash.addNode("192.168.1.5");

    dataHash.defaultNode("192.168.1.3");

    for (int i = 0; i < 100; i++) {
      String node = dataHash.getNode("192.168.3." + i);
      Assert.assertNotNull(node);
    }

    System.out.println("----------------");

    dataHash.dataDown("192.168.1.3");
    System.out.println("节点下线");
    for (int i = 0; i < 100; i++) {
      String node = dataHash.getNode("192.168.3." + i);
      Assert.assertNotEquals(node, "192.168.1.3");
    }
  }
}
