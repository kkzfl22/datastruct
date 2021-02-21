package com.liujun.datastruct.base.datastruct.hash.consistenthash;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash算法，使用环路节点进行操作,处理的区别在于当节点找不到时的操作
 *
 * <p>如果是环路节点获取，相对简单
 *
 * @author liujun
 * @version 0.0.1
 */
public class ConsistentHashCycle {

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
   * 执行节点下线操作
   *
   * @param serviceIp
   */
  public void dataDown(String serviceIp) {
    int hashCode = Hashing.murmur3_32().hashString(serviceIp, StandardCharsets.UTF_8).asInt();
    // 下线节点
    SORT_MAP.remove(hashCode);
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

    // 当节点获取不到时，获取首个节点作为默认节点
    if (dataNode.isEmpty()) {
      return SORT_MAP.get(SORT_MAP.firstKey());
    }

    // 如果能获取到，则返回对应的首个节点
    return dataNode.get(dataNode.firstKey());
  }
}
