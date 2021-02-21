package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash算法带有虚拟节点
 *
 * @author liujun
 * @version 0.0.1
 */
public class ConsistentHashVirtualNodeCycle {

  /** 用于记录当前一致性hasp的节点信息 */
  private static final SortedMap<Integer, String> SORT_MAP = new TreeMap<>();

  /** 虚拟节点的个数 */
  private static final int VIRTUAL_NODE = 100;

  /** 添加的字符 */
  private static final String RAND_VALUE = "DATA_";

  /**
   * 添加节点
   *
   * @param dataServerIp
   */
  public void addNode(String dataServerIp) {
    for (int i = 0; i < VIRTUAL_NODE; i++) {
      String dataKey = dataServerIp + RAND_VALUE + i;
      int hashCode = Hashing.murmur3_32().hashString(dataKey, StandardCharsets.UTF_8).asInt();
      SORT_MAP.put(hashCode, dataServerIp);
    }
  }

  /**
   * 执行节点下线操作
   *
   * @param serviceIp
   */
  public void dataDown(String serviceIp) {
    for (int i = 0; i < VIRTUAL_NODE; i++) {
      String key = serviceIp + RAND_VALUE + i;
      int hashCode = Hashing.murmur3_32().hashString(key, StandardCharsets.UTF_8).asInt();
      // 下线节点
      SORT_MAP.remove(hashCode);
    }
  }

  /**
   * 获取客户端的节点信息
   *
   * @param key key的信息
   * @return
   */
  public String getNode(String key) {
    int hashCode = Hashing.murmur3_32().hashString(key, StandardCharsets.UTF_8).asInt();
    SortedMap<Integer, String> dataNode = SORT_MAP.tailMap(hashCode);
    // 如果获取不到节点，则返回第一个节点
    if (dataNode.isEmpty()) {
      return SORT_MAP.get(SORT_MAP.firstKey());
    }
    return dataNode.get(dataNode.firstKey());
  }
}
