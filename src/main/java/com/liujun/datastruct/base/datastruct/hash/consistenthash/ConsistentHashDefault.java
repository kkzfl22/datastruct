package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一致性hash算法，使用默认的最式节点
 *
 * <p>这是手动指定默认的节点，但是如果忘记指定了，会造成获取不到服务器节点的情况
 *
 * @author liujun
 * @version 0.0.1
 */
public class ConsistentHashDefault {

  /** 用于记录当前一致性hasp的节点信息 */
  private static final SortedMap<Integer, String> SORT_MAP = new TreeMap<>();

  /**
   * 添加节点
   *
   * @param dataServerIp
   */
  public void addNode(String dataServerIp) {
    int hashCode = Hashing.murmur3_32().hashString(dataServerIp, StandardCharsets.UTF_8).asInt();
    SORT_MAP.put(hashCode, dataServerIp);
  }

  /**
   * 默认的服务器的IP
   *
   * @param serverIp
   */
  public void defaultNode(String serverIp) {
    SORT_MAP.put(Integer.MAX_VALUE, serverIp);
  }

  /**
   * 执行节点下线操作
   *
   * @param serviceIp
   */
  public void dataDown(String serviceIp) {
    int hashCode = Hashing.murmur3_32().hashString(serviceIp, StandardCharsets.UTF_8).asInt();
    // 下线节点
    SORT_MAP.remove(hashCode);
    // 检查下线节点是否为默认节点,如果是，也需要下线操作
    String dataIp = SORT_MAP.get(Integer.MAX_VALUE);
    if (serviceIp.equals(dataIp)) {
      SORT_MAP.remove(Integer.MAX_VALUE);
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
    return dataNode.get(dataNode.firstKey());
  }
}
