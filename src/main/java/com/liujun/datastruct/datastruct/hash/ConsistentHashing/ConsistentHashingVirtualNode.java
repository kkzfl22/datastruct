package com.liujun.datastruct.datastruct.hash.ConsistentHashing;

import java.util.*;

/**
 * 虚拟节点的一致性hash算法
 *
 * <p>解决不带虚拟节点的分配不均的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/10
 */
public class ConsistentHashingVirtualNode {

  //  /** hash环的服务器列表 */
  private static String[] servers = {
    "192.168.3.1:8080", "192.168.3.2:8081", "192.168.99.100:8083", "192.168.99.200:80"
  };

  /** 服务器节点信息，有可能涉及动态上线与下级节点，故采用linkedList比较合适 */
  private static final List<String> SERVICE_NODE = new LinkedList<>();

  /** 使用有序数据的节点 */
  private static final SortedMap<Integer, String> VIRTUAL_NODE = new TreeMap<>();

  /** 一台服务器对应的虚拟节点数 */
  private static final int VIRTUAL_NUM = 20;

  static {

    // 将服务器加入服务器节点
    for (int i = 0; i < servers.length; i++) {
      SERVICE_NODE.add(servers[i]);
    }

    // 2，生成虚拟服务器节点
    for (String serverIp : SERVICE_NODE) {
      // 生成虚拟节点数
      for (int i = 0; i < VIRTUAL_NUM; i++) {
        String virtualNodeName = serverIp + "__" + i;

        // 使用一片服务器来对应一个服务器IP,即可
        VIRTUAL_NODE.put(HashCode.getHash(virtualNodeName), serverIp);
      }
    }

    // 添加一个最大值，防止找不到服务器节点
    VIRTUAL_NODE.put(Integer.MAX_VALUE, servers[servers.length - 1]);
  }

  /**
   * 添加服务器节点
   *
   * @param serviceIp 服务的IP
   */
  public static void addNode(String serviceIp) {
    // 生成虚拟节点数
    for (int i = 0; i < VIRTUAL_NUM; i++) {
      String virtualNodeName = serviceIp + "__" + i;
      // 使用一片服务器来对应一个服务器IP,即可
      VIRTUAL_NODE.put(HashCode.getHash(virtualNodeName), serviceIp);
    }
  }

  /**
   * 删除服务的节点
   *
   * @param serviceIp 指定的服务IP
   */
  public static void deleteNode(String serviceIp) {

    // 生成虚拟节点数
    for (int i = 0; i < VIRTUAL_NUM; i++) {
      String virtualNodeName = serviceIp + "__" + i;

      // 使用一片服务器来对应一个服务器IP,即可
      VIRTUAL_NODE.remove(HashCode.getHash(virtualNodeName));
    }
  }

  public static String getNode(String requ) {
    // 1.生成hash码
    int hash = HashCode.getHash(requ);
    // 1,得到大于该Key的所有map
    SortedMap<Integer, String> sortMap = VIRTUAL_NODE.tailMap(hash);
    // 返回对应的服务器IP信息
    return VIRTUAL_NODE.get(sortMap.firstKey());
  }

  public static void main(String[] args) {
    Map<String, Integer> map = new HashMap<>();

    for (int i = 0; i < 100000; i++) {
      String node = getNode(String.valueOf(i));
      //      System.out.println(
      //          "请求:" + i + ",hash值:" + HashCode.getHash(String.valueOf(i)) + ",路由到的节点为:" + node);

      if (map.get(node) == null) {
        map.put(node, 1);
      } else {
        map.put(node, map.get(node) + 1);
      }
    }

    System.out.println("处理：" + map);
    addNode("192.168.99.10");
    deleteNode("192.168.99.100:8083");

    for (int i = 10000; i < 800000; i++) {
      String node = getNode(String.valueOf(i));
      //      System.out.println(
      //          "请求:" + i + ",hash值:" + HashCode.getHash(String.valueOf(i)) + ",路由到的节点为:" + node);

      if (map.get(node) == null) {
        map.put(node, 1);
      } else {
        map.put(node, map.get(node) + 1);
      }
    }

    System.out.println("处理：" + map);
  }
}
