package com.liujun.datastruct.base.datastruct.hash.ConsistentHashing;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 不带虚拟节点的一致性hash算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/10
 */
public class ConsistentHashingWithoutVirtualNode {

  //  /** hash环的服务器列表 */
  private static String[] servers = {
    "192.168.3.1", "192.168.3.2", "192.168.99.100", "192.168.99.200"
  };

  /** key 为服务器的hash哈希值，value 为服务器的名称 */
  private static SortedMap<Integer, String> SERVER_SORT_MAP = new TreeMap<>();

  static {
    for (int i = 0; i < servers.length; i++) {
      int hash = HashCode.getHash(servers[i]);

      // System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
      SERVER_SORT_MAP.put(hash, servers[i]);
    }

    // 设置默认的服务器
    SERVER_SORT_MAP.put(Integer.MAX_VALUE, servers[servers.length - 1]);
  }

  public static String getNode(String node) {
    // 1,得到带路由节点的hash值
    int hashCode = HashCode.getHash(node);
    // 2，得到所有大于该hash的所有map
    SortedMap<Integer, String> subMap = SERVER_SORT_MAP.tailMap(hashCode);
    // 第一个Key就是顺时针过去离node最近的那个结点
    Integer nodexIndex = subMap.firstKey();
    return subMap.get(nodexIndex);
  }

  public static void main(String[] args) {
    String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
    for (int i = 0; i < nodes.length; i++) {
      System.out.println(
          "["
              + nodes[i]
              + "]的hash值为"
              + HashCode.getHash(nodes[i])
              + ", 被路由到结点["
              + getNode(nodes[i])
              + "]");
    }
  }
}
